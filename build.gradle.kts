import xyz.xenondevs.novagradle.task.PluginDependency

group = "dev.melncat" 
version = "0.0.1"

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.nova)
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)
    implementation(libs.nova)
    compileOnly("com.github.gecolay.GSit:core:1.12.0")
    
}

addon {
    name = project.name.replaceFirstChar(Char::uppercase)
    version = project.version.toString()
    main = "dev.melncat.paperasylum.PaperAsylum"
    bootstrapper = "dev.melncat.paperasylum.bootstrap.PaperAsylumBootstrap"
    dependency("GSit", PluginDependency.Stage.SERVER, PluginDependency.Load.OMIT, true, true)
    
    val outDir = project.findProperty("outDir")
    if (outDir is String)
        destination.set(File(outDir))
}

afterEvaluate {
    tasks.getByName<Jar>("jar") {
        archiveClassifier = ""
    }
}