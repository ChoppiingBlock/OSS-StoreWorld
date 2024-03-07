package org.spring.tool.Impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.tool.FilePartitioningUtil;
import org.spring.tool.partitioningutil.ReedSolomon;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * TODO
 *
 * @Description 通过ec纠删码算法冗余快
 * @Author ZW
 * @Date 2023/3/3 9:30
 **/
@Component
public class EcPartitioningUtil implements FilePartitioningUtil {
    Logger logger = LoggerFactory.getLogger(FilePartitioningUtil.class);
    /**
     * 源数据的分片数量
     */
    private final int dataShards = 4;
    /**
     * 冗余的碎片
     */
    private final int partShards = 2;

    public static final int BYTES_IN_INT = 4;


    @Override
    public boolean encoder(String filepath) {
        return baseEncoder(filepath, this.dataShards, this.partShards);
    }

    @Override
    public boolean decoder(String filepath) {
        return baseDecoder(filepath, this.dataShards, this.partShards);
    }

    @Override
    public boolean encoder(String filepath, int dataShards, int partShards) {
        return baseEncoder(filepath, dataShards, partShards);
    }

    @Override
    public boolean decoder(String filepath, int dataShards, int partShards) {
        return baseDecoder(filepath, dataShards, partShards);
    }


    private boolean baseEncoder(String filepath, int dataShards, int partShards) {
        int totalShards = dataShards + partShards;
        final File inputFile = new File(filepath);
        if (!inputFile.exists()) {
            logger.error("Cannot read input file: {}", inputFile);
            return false;
        }

        // Get the size of the input file.  (Files bigger that
        // Integer.MAX_VALUE will fail here!)
        final int fileSize = (int) inputFile.length();

        // Figure out how big each shard will be.  The total size stored
        // will be the file size (8 bytes) plus the file.
        final int storedSize = fileSize + BYTES_IN_INT;
        final int shardSize = (storedSize + dataShards - 1) / dataShards;

        // Create a buffer holding the file size, followed by
        // the contents of the file.
        final int bufferSize = shardSize * dataShards;
        final byte[] allBytes = new byte[bufferSize];
        ByteBuffer.wrap(allBytes).putInt(fileSize);
        InputStream in = null;
        try {
            in = new FileInputStream(inputFile);
            int bytesRead = in.read(allBytes, BYTES_IN_INT, fileSize);
            if (bytesRead != fileSize) {
                throw new IOException("not enough bytes read");
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Make the buffers to hold the shards.
        byte[][] shards = new byte[totalShards][shardSize];

        // Fill in the data shards
        for (int i = 0; i < dataShards; i++) {
            System.arraycopy(allBytes, i * shardSize, shards[i], 0, shardSize);
        }

        // Use Reed-Solomon to calculate the parity.
        ReedSolomon reedSolomon = ReedSolomon.create(dataShards, partShards);
        reedSolomon.encodeParity(shards, 0, shardSize);

        // Write out the resulting files.
        for (int i = 0; i < totalShards; i++) {
            File outputFile = new File(
                    inputFile.getParentFile(),
                    inputFile.getName() + "." + i);
            OutputStream out = null;
            try {
                out = new FileOutputStream(outputFile);
                out.write(shards[i]);
            } catch (IOException e) {
                return false;
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }

    private boolean baseDecoder(String filepath, int dataShards, int partShards) {
        int totalShards = dataShards + partShards;
        final File originalFile = new File(filepath);
        if (originalFile.exists()) {
            originalFile.delete();
        }
        final byte[][] shards = new byte[totalShards][];
        final boolean[] shardPresent = new boolean[totalShards];
        int shardSize = 0;
        int shardCount = 0;
        for (int i = 0; i < totalShards; i++) {
            File shardFile = new File(
                    originalFile.getParentFile(),
                    originalFile.getName() + "." + i);
            System.out.println(shardFile.getParentFile().getName());
            if (shardFile.exists()) {
                shardSize = (int) shardFile.length();
                shards[i] = new byte[shardSize];
                shardPresent[i] = true;
                shardCount += 1;
                InputStream in = null;
                try {
                    in = new FileInputStream(shardFile);
                    in.read(shards[i], 0, shardSize);

                } catch (IOException e) {
                    return false;
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                logger.info("Read {}", shardFile);
            }
        }
        // We need at least DATA_SHARDS to be able to reconstruct the file.
        if (shardCount < dataShards) {
            logger.error("分块碎片不足");
            return false;
        }
        // Make empty buffers for the missing shards.
        for (int i = 0; i < totalShards; i++) {
            if (!shardPresent[i]) {
                shards[i] = new byte[shardSize];
            }
        }
        // Use Reed-Solomon to fill in the missing shards
        ReedSolomon reedSolomon = ReedSolomon.create(dataShards, partShards);
        reedSolomon.decodeMissing(shards, shardPresent, 0, shardSize);

        byte[] allBytes = new byte[shardSize * dataShards];
        for (int i = 0; i < dataShards; i++) {
            System.arraycopy(shards[i], 0, allBytes, shardSize * i, shardSize);
        }

        int fileSize = ByteBuffer.wrap(allBytes).getInt();

        File decodedFile = new File(originalFile.getParentFile(), originalFile.getName() + ".decoded");
        OutputStream out = null;
        try {
            out = new FileOutputStream(decodedFile);
            out.write(allBytes, BYTES_IN_INT, fileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("Wrote {}", decodedFile);
        return true;
    }
}
