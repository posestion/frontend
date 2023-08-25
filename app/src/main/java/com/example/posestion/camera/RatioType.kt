package com.example.posestion.camera

enum class RatioType{
    Ratio9_16 {
        override fun next(): RatioType {
            return Ratio4_3
        }
    },
    Ratio4_3 {
        override fun next(): RatioType {
            return Ratio9_16
        }
    };
//    Ratio1_1 {
//        override fun next(): RatioType {
//            return Ratio_full
//        }
//    },
//    Ratio_full {
//        override fun next(): RatioType {
//            return Ratio9_16
//        }
//    };
    abstract fun next(): RatioType
}
