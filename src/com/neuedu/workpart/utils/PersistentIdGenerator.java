package com.neuedu.workpart.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 持久化整数ID生成器（单例模式）。
 * <p>生成全局唯一的自增整数ID，ID值持久化到文件中，程序重启后不会丢失。</p>
 * <p>线程安全，使用{@link ReentrantLock}保证并发环境下的ID唯一性。</p>
 * <p>安全处理空文件、数字格式异常等边界情况，解析失败时从0开始。</p>
 *
 * @author QICHAN
 */
public class PersistentIdGenerator {
    /** 存储最后ID的文件路径 */
    private static final String ID_FILE = "data/last-id.txt";
    /** 当前最大ID值 */
    private int currentId;
    /** 可重入锁，保证线程安全 */
    private final ReentrantLock lock = new ReentrantLock();

    /** 单例实例（饿汉式） */
    private static final PersistentIdGenerator INSTANCE = new PersistentIdGenerator();

    /**
     * 私有构造方法，从文件加载最后使用的ID值
     */
    private PersistentIdGenerator() {

        this.currentId = loadLastIdFromFile();
    }
    /**
     * 获取单例实例
     *
     * @return PersistentIdGenerator的唯一实例
     */
    public static PersistentIdGenerator getInstance() {

        return INSTANCE;
    }
    /**
     * 生成下一个自增ID并持久化到文件。
     * <p>线程安全方法，使用ReentrantLock保证同一时刻只有一个线程能生成ID。</p>
     *
     * @return 新的自增ID值
     */
    public int nextId() {
        lock.lock();
        try {
            currentId++;
            saveIdToFile(currentId);
            return currentId;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 安全加载ID（修复空文件、格式错误）
     */
    private int loadLastIdFromFile() {
        File file = new File(ID_FILE);
        if (!file.exists()) {
            return 0;
        }

        try {
            String content = Files.readString(file.toPath()).trim();

            // 空内容 → 返回0
            if (content.isEmpty()) {
                return 0;
            }

            // 安全解析
            return Integer.parseInt(content);

        } catch (Exception e) {
            // 解析失败 → 从0开始，不抛错
            return 0;
        }
    }

    /**
     * 保存最新ID
     */
    private void saveIdToFile(long id) {
        try (FileWriter writer = new FileWriter(ID_FILE)) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            throw new RuntimeException("保存ID失败", e);
        }
    }

    /** 测试入口：连续生成5个ID并输出 */
    public static void main(String[] args) {
        PersistentIdGenerator generator = PersistentIdGenerator.getInstance();
        for (int i = 0; i < 5; i++) {
            System.out.println("生成ID: " + generator.nextId());
        }
    }
}