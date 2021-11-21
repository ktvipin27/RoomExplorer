/*
 * Copyright 2021 Vipin KT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ktvipin.roominspector.query

import com.ktvipin.roominspector.util.forEachIndexed

/**
 * A class which is responsible for building the SQL queries with given arguments.
 *
 * Created by Vipin KT on 12/05/20
 */
object QueryBuilder {
    /**
     * Query to get all table names in the database.
     */
    const val GET_TABLE_NAMES = "SELECT name _id FROM sqlite_master WHERE type ='table'"

    /**
     * Query to get all column names of the table.
     *
     * @param tableName name of the table
     * @return query
     */
    infix fun getColumnNames(tableName: String) = "PRAGMA table_info($tableName)"

    /**
     * Query to get all values of the table.
     *
     * @param tableName name of the table
     * @return query
     */
    infix fun getAllValues(tableName: String) = "SELECT * FROM $tableName"

    /**
     * Query to drop the table.
     *
     * @param tableName name of the table
     * @return query
     */
    infix fun dropTable(tableName: String) = "DROP TABLE $tableName"

    /**
     * Query to clear all values of the table.
     *
     * @param tableName name of the table
     * @return query
     */
    infix fun deleteTable(tableName: String) = "DELETE FROM $tableName"

    /**
     * Query to insert values in to the table
     *
     * @param tableName name of the table
     * @param values list of values to be inserted
     * @return query
     */
    fun insert(tableName: String, values: List<String>): String {
        var insertQuery = "INSERT INTO $tableName VALUES("
        values.forEachIndexed { index, value ->
            insertQuery += "'$value'"
            if (index != values.size - 1)
                insertQuery += ","
        }
        insertQuery += ")"
        return insertQuery
    }

    /**
     * Query to update values of the table.
     *
     * @param tableName name of the table
     * @param columnNames list of column names
     * @param oldValues list of current values
     * @param newValues list of values to be updated
     * @return query
     */
    fun updateTable(
        tableName: String,
        columnNames: List<String>,
        oldValues: List<String>,
        newValues: List<String>
    ): String {
        var query = "Update $tableName set "
        Pair(columnNames, newValues).forEachIndexed { index, columnName, value ->
            query += "$columnName = '$value'"
            if (index != columnNames.size - 1)
                query += ", "
        }
        query += " where "
        Pair(columnNames, oldValues).forEachIndexed { index, columnName, value ->
            query += "$columnName = '$value'"
            if (index != columnNames.size - 1)
                query += " AND "
        }
        return query
    }

    /**
     * Query to delete values of a row.
     *
     * @param tableName name of the table
     * @param columnNames list of column names
     * @param values list of values to be deleted
     * @return query
     */
    fun deleteRow(
        tableName: String,
        columnNames: List<String>,
        values: List<String>
    ): String {
        var query = "DELETE FROM $tableName where "
        Pair(columnNames, values).forEachIndexed { index, columnName, value ->
            query += "$columnName = '$value'"
            if (index != columnNames.size - 1)
                query += " AND "
        }
        return query
    }
}
