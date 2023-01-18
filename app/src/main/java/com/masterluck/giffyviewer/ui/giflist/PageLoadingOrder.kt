package com.masterluck.giffyviewer.ui.giflist

enum class PageLoadingOrder(val direction: Int) {
    PREVIOUS(-1),
    NEW(0),
    NEXT(1),
}