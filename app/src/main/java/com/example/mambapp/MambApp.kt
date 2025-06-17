package com.example.mambapp

import android.app.Application

class MambApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}