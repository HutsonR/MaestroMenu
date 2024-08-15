package com.example.databasexmlcourse.features.common.dialogs.adapter.models

internal class DialogSearcherListUiConverter {

    fun convertToListItem(items: List<DialogSearcherModel>): List<DialogSearcherListItem> {
        return mutableListOf<DialogSearcherListItem>().apply {
            for (item in items) {
                add(item.convertToListItem())
            }
        }
    }

    fun convertToModelItem(item: DialogSearcherListItem) =
        DialogSearcherModel(
            id = item.id,
            text = item.text
        )

    private fun DialogSearcherModel.convertToListItem() =
        DialogSearcherListItem(
            id = this.id,
            text = this.text
        )
}