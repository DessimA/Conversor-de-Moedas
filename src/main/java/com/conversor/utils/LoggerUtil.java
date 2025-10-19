package com.conversor.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {
    private static final String LOG_FILE_PATH = "logs/conversoes.log";
    private static final int MAX_LOG_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final int MAX_LOG_FILE_COUNT = 5; // Rotate 5 files

    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false); // Prevent logging to console by default

        // Check if handlers are already added to avoid duplicates
        if (logger.getHandlers().length == 0) {
            try {
                FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, MAX_LOG_FILE_SIZE, MAX_LOG_FILE_COUNT, true);
                fileHandler.setFormatter(new CustomFormatter());
                logger.addHandler(fileHandler);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Erro ao configurar o logger de arquivo", e);
            }
        }
        return logger;
    }

    private static class CustomFormatter extends Formatter {
        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        @Override
        public String format(LogRecord record) {
            return String.format("[%s] [%s] %s%n",
                    LocalDateTime.now().format(DATE_TIME_FORMATTER),
                    record.getLevel().getName(),
                    formatMessage(record));
        }
    }
}
