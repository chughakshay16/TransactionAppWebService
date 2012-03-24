package com.project.dao;

import java.util.Date;

public interface CreateUniversalTables
{
	public boolean createDatabase();
	public boolean createAccountsTable();
	public boolean createSuggestionsTable();
	public boolean createSuggestionDetailsTable();
	public boolean createSuggestionTransactionsTable();
}