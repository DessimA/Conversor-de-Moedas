package com.conversor.models;

import java.util.ArrayList;
import java.util.Collections;
import com.conversor.utils.LoggerUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoricoConversao {
    private static final Logger logger = LoggerUtil.getLogger(HistoricoConversao.class.getName());
    private static final String HISTORICO_FILE = "data/historico.json";
    private static final String HISTORICO_BACKUP_FILE = "data/historico.json.bak";
    private final Gson gson;
    private List<ConversaoResultado> conversoes;

    public HistoricoConversao() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.conversoes = new ArrayList<>();
        carregarDeArquivo();
    }

    public void adicionarConversao(ConversaoResultado resultado) {
        this.conversoes.add(resultado);
        salvarEmArquivo();
    }

    public List<ConversaoResultado> getTodasConversoes() {
        return Collections.unmodifiableList(conversoes);
    }

    public List<ConversaoResultado> getUltimasConversoes(int n) {
        int startIndex = Math.max(0, conversoes.size() - n);
        return Collections.unmodifiableList(conversoes.subList(startIndex, conversoes.size()));
    }

    public void limparHistorico() {
        this.conversoes.clear();
    }

    public boolean estaVazio() {
        return conversoes.isEmpty();
    }

    public void salvarEmArquivo() {
        try {
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            File historicoFile = new File(HISTORICO_FILE);
            File historicoBackupFile = new File(HISTORICO_BACKUP_FILE);

            // Create backup before saving
            if (historicoFile.exists()) {
                if (historicoBackupFile.exists()) {
                    historicoBackupFile.delete();
                }
                historicoFile.renameTo(historicoBackupFile);
            }

            try (FileWriter writer = new FileWriter(historicoFile)) {
                gson.toJson(conversoes, writer);
                logger.log(Level.INFO, "Histórico salvo em: {0}", HISTORICO_FILE);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao salvar histórico em arquivo: {0}", e.getMessage());
        }
    }

    private void carregarDeArquivo() {
        File historicoFile = new File(HISTORICO_FILE);
        File historicoBackupFile = new File(HISTORICO_BACKUP_FILE);

        if (!historicoFile.exists() && !historicoBackupFile.exists()) {
            logger.log(Level.INFO, "Nenhum arquivo de histórico encontrado. Iniciando com histórico vazio.");
            return;
        }

        try (FileReader reader = new FileReader(historicoFile)) {
            Type listType = new TypeToken<List<ConversaoResultado>>() {}.getType();
            this.conversoes = gson.fromJson(reader, listType);
            if (this.conversoes == null) {
                this.conversoes = new ArrayList<>();
            }
            logger.log(Level.INFO, "Histórico carregado de: {0}", HISTORICO_FILE);
        } catch (IOException | JsonSyntaxException e) {
            logger.log(Level.WARNING, "Erro ao carregar histórico de {0}: {1}. Tentando carregar do backup...", new Object[]{HISTORICO_FILE, e.getMessage()});
            try (FileReader backupReader = new FileReader(historicoBackupFile)) {
                Type listType = new TypeToken<List<ConversaoResultado>>() {}.getType();
                this.conversoes = gson.fromJson(backupReader, listType);
                if (this.conversoes == null) {
                    this.conversoes = new ArrayList<>();
                }
                logger.log(Level.INFO, "Histórico carregado do backup: {0}", HISTORICO_BACKUP_FILE);
            } catch (IOException | JsonSyntaxException backupE) {
                logger.log(Level.SEVERE, "Erro ao carregar histórico do backup {0}: {1}. Iniciando com histórico vazio.", new Object[]{HISTORICO_BACKUP_FILE, backupE.getMessage()});
                this.conversoes = new ArrayList<>();
            }
        }
    }
}