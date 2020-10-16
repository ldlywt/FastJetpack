package com.aisier.architecture.anno

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class FragmentConfiguration(

        val shareViewModel: Boolean = false,

        val useEventBus: Boolean = false
)