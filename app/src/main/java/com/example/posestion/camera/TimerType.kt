package com.example.posestion.camera

enum class TimerType {
    OFF {
        override fun next(): TimerType {
            return S3
        }

        override fun second(): Int {
            return 0
        }
    },
    S3 {
        override fun next(): TimerType {
            return S5
        }

        override fun second(): Int {
            return 3
        }
    },
    S5 {
        override fun next(): TimerType {
            return S10
        }

        override fun second(): Int {
            return 5
        }
    },
    S10 {
        override fun next(): TimerType {
            return OFF
        }

        override fun second(): Int {
            return 10
        }
    };
    abstract fun next(): TimerType
    abstract fun second(): Int
}