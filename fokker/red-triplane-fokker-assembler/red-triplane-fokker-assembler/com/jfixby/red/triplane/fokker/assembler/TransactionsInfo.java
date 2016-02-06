package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;
import java.util.Vector;

import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;

public class TransactionsInfo {

	public static final String TRANSACTIONS_RECORD_FILE = "executed.transactions";

	public static TransactionsInfo loadLast() throws IOException {
		File file = LocalFileSystem.ApplicationHome().child(TRANSACTIONS_RECORD_FILE);
		if (!file.exists()) {
			return null;
		}
		String data = file.readToString();
		TransactionsInfo info = Json.deserializeFromString(TransactionsInfo.class, data);
		return info;
	}

	public Vector<ExecutedTransaction> listTransactions() {
		return transacions;
	}

	public Vector<ExecutedTransaction> transacions = new Vector<ExecutedTransaction>();

	public static void save(TransactionsInfo transaction) throws IOException {
		String data = Json.serializeToString(transaction);
		File file = LocalFileSystem.ApplicationHome().child(TRANSACTIONS_RECORD_FILE);
		file.writeString(data);
	}

}
