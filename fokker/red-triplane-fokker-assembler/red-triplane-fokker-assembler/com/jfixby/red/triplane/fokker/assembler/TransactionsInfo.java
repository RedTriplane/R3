
package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;
import java.util.ArrayList;

import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;

public class TransactionsInfo {

	public static final String TRANSACTIONS_RECORD_FILE = "executed.transactions";

	public static TransactionsInfo loadLast () throws IOException {
		final File file = LocalFileSystem.ApplicationHome().child(TRANSACTIONS_RECORD_FILE);
		if (!file.exists()) {
			return null;
		}
		final String data = file.readToString();
		final TransactionsInfo info = Json.deserializeFromString(TransactionsInfo.class, data);
		return info;
	}

	public ArrayList<ExecutedTransaction> listTransactions () {
		return this.transactions;
	}

	public ArrayList<ExecutedTransaction> transactions = new ArrayList<ExecutedTransaction>();

	public static void save (final TransactionsInfo transaction) throws IOException {
		final String data = Json.serializeToString(transaction).toString();
		final File file = LocalFileSystem.ApplicationHome().child(TRANSACTIONS_RECORD_FILE);
		file.writeString(data);
	}

}
