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

package com.ktvipin.roominspector.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ktvipin.roominspector.R

/**
 * A custom [TableLayout] class having functionality for creating table by using given rows and columns.
 *
 * Created by Vipin KT on 15/05/20
 */
class TableView(context: Context) : TableLayout(context) {

    private val tableRowMinHeight by lazy {
        context.resources.getDimension(R.dimen.ri_min_height_table_row).toInt()
    }
    private val tableRowBackground by lazy {
        ContextCompat.getColor(
            context,
            R.color.ri_color_table_row
        )
    }

    /**
     * Creates a [TextView] to display column names in the table.
     *
     * @param text column name
     * @return TableHeader
     */
    private fun tableHeader(text: String) = TextView(context)
        .apply {
            minHeight = tableRowMinHeight
            gravity = Gravity.CENTER_VERTICAL
            setPadding(10, 10, 10, 10)
            this.text = text
            setTextColor(Color.BLACK)
            typeface = Typeface.DEFAULT_BOLD
        }

    /**
     * Creates a [TextView] to display values in the table.
     *
     * @param text field value
     * @return TableTuple
     */
    private fun tableTuple(text: String) = TextView(context)
        .apply {
            minHeight = tableRowMinHeight
            gravity = Gravity.CENTER_VERTICAL
            setPadding(10, 10, 10, 10)
            this.text = text
            setTextColor(Color.BLACK)
            typeface = Typeface.DEFAULT
        }

    /**
     * creates a row of tuples by using the provided values.
     *
     * @param values list of values to display
     * @param isHeader if true, creates header row, else normal row
     * @return TableRow
     */
    private fun tableRow(values: List<String>, isHeader: Boolean = false) =
        TableRow(context).apply {
            setPadding(0, 2, 0, 2)
            values.forEach {
                if (isHeader)
                    addView(tableHeader(it))
                else
                    addView(tableTuple(it))
            }
        }

    /**
     * Creates a table with provided values.
     *
     * @param columnNames list of column names
     * @param rows list of rows, each row contains list of fields
     * @param onClickAction function to get called on clicking the row
     * @param onLongClickAction function to get called on long clicking the row
     * @return [TableLayout] containing rows and columns filled with the provided values
     */
    fun create(
        columnNames: List<String>,
        rows: List<List<String>>,
        onClickAction: (pos: Int) -> Unit,
        onLongClickAction: (pos: Int) -> Unit
    ): TableView {
        addView(tableRow(columnNames, true))
        rows.forEachIndexed { index, list ->
            val tableRow = tableRow(list, false).apply {
                setOnClickListener { onClickAction(index) }
                setOnLongClickListener {
                    onLongClickAction(index)
                    true
                }
                if (index % 2 != 0)
                    setBackgroundColor(tableRowBackground)
            }
            addView(tableRow)
        }
        return this
    }
}
