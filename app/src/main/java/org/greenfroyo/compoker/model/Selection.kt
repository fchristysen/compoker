package org.greenfroyo.compoker.model

data class Selection(private val value: Array<Boolean> = arrayOf(false, false, false, false, false)) {

    fun isSelected(position: Int) = value[position]

    fun toggle(position: Int): Selection {
        val newVal = value.clone()
        newVal[position] = !newVal[position]
        return Selection(newVal)
    }
}