package com.mobile.phonelogs.domain.entities

data class ResultData<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> complete(data: T): ResultData<T> {
            return ResultData(Status.COMPLETED, null, null)
        }

        fun <T> success(data: T?): ResultData<T> {
            return ResultData(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): ResultData<T> {
            return ResultData(Status.ERROR, null, msg)
        }

        fun <T> loading(): ResultData<T> {
            return ResultData(Status.LOADING, null, null)
        }
    }
}

// An enum to store the
// current state of api call
enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    COMPLETED
}