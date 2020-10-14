package com.fastaac.base.anno

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ActivityConfiguration(

        val useEventBus: Boolean = false,

        val needLogin: Boolean = true
)