package com.example.basekotlinkoin.data.source.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                CREATE TABLE manage_usage_data_table (
                    productId TEXT PRIMARY KEY NOT NULL,
                    packageName TEXT,
                    active INTEGER,
                    month TEXT,
                    packageData TEXT
                )
            """.trimIndent()
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                CREATE TABLE activity_log_table (
                    subscribedDate PRIMARY KEY TEXT NOT NULL,                    
                    activity TEXT
                )
            """.trimIndent()
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                DROP TABLE addon_popular
            """.trimIndent()
        )
        database.execSQL(
            """
                CREATE TABLE addon_popular (
                    addonId TEXT PRIMARY KEY NOT NULL,
                    category TEXT NOT NULL,
                    imageUrl TEXT NOT NULL,
                    mostPopular INTEGER NOT NULL,
                    packageName TEXT NOT NULL,
                    packageDuration TEXT NOT NULL,
                    startFrom INTEGER NOT NULL,
                    subCategory TEXT NOT NULL
                )
            """.trimIndent()
        )
        database.execSQL(
            """
                DROP TABLE addon_latest
            """.trimIndent()
        )
        database.execSQL(
            """
                CREATE TABLE addon_latest (
                    addonId TEXT PRIMARY KEY NOT NULL,
                    mostPopular INTEGER NOT NULL,
                    category TEXT NOT NULL,
                    subCategory TEXT NOT NULL,
                    imageUrl TEXT NOT NULL,
                    packageName TEXT NOT NULL
                )
            """.trimIndent()
        )
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                DROP TABLE latest_new_table
            """.trimIndent()
        )
        database.execSQL(
            """
                CREATE TABLE latest_new_table (
                    bannerId TEXT PRIMARY KEY,
                    title TEXT,
                    destination_website TEXT,
                    destination_mobile TEXT,
                    image_website TEXT,
                    image_mobile TEXT
                )
            """.trimIndent()
        )
        database.execSQL(
            """
                DROP TABLE latest_offer_table
            """.trimIndent()
        )
        database.execSQL(
            """
                CREATE TABLE latest_offer_table (
                    bannerLatestOfferId TEXT PRIMARY KEY,
                    title TEXT,
                    destination_website TEXT,
                    destination_mobile TEXT,
                    image_website TEXT,
                    image_mobile TEXT
                )
            """.trimIndent()
        )
        database.execSQL(
            """
                DROP TABLE subscription_table
            """.trimIndent()
        )
        database.execSQL(
            """
                CREATE TABLE subscription_table (
                    bannerMovieId TEXT PRIMARY KEY,
                    title TEXT,
                    destination_website TEXT,
                    destination_mobile TEXT,
                    image_website TEXT,
                    image_mobile TEXT
                )
            """.trimIndent()
        )
    }
}