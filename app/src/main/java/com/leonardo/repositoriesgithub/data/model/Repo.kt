package com.leonardo.repositoriesgithub.data.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Repo(
    val id: Long,
    val name: String?,
    val fullName: String?,
    val private: Boolean,
    val owner: Owner?,
    @SerializedName("html_url")
    val htmlURL: String?,
    val description: String?,
    val size: Long,
    @SerializedName("stargazers_count")
    val stargazersCount: Long,
    val watchersCount: Long,
    val language: String?,
    val forksCount: Long,
    val forks: Long,
    val openIssues: Long,
    val watchers: Long,
    @SerializedName("default_branch")
    val defaultBranch: String? ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Owner::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(fullName)
        parcel.writeByte(if (private) 1 else 0)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(htmlURL)
        parcel.writeString(description)
        parcel.writeLong(size)
        parcel.writeLong(stargazersCount)
        parcel.writeLong(watchersCount)
        parcel.writeString(language)
        parcel.writeLong(forksCount)
        parcel.writeLong(forks)
        parcel.writeLong(openIssues)
        parcel.writeLong(watchers)
        parcel.writeString(defaultBranch)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repo> {
        override fun createFromParcel(parcel: Parcel): Repo {
            return Repo(parcel)
        }

        override fun newArray(size: Int): Array<Repo?> {
            return arrayOfNulls(size)
        }
    }
}