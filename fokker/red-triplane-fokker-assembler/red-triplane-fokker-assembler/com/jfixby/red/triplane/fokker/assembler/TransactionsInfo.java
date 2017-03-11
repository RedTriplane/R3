package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;
import java.util.Vector;

import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;

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
		return transactions;
	}

	public Vector<ExecutedTransaction> transactions = new Vector<ExecutedTransaction>();

	public static void save(TransactionsInfo transaction) throws IOException {
		String data = Json.serializeToString(transaction).toString();
		File file = LocalFileSystem.ApplicationHome().child(TRANSACTIONS_RECORD_FILE);
		file.writeString(data);
	}

}