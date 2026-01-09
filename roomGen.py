import argparse
import os

def main():
    parser = argparse.ArgumentParser(description="Generador de código Room")
    parser.add_argument(
        "--test",
        action="store_true",
        help="Ejecuta el programa en modo test"
    )

    args = parser.parse_args()

    if args.test:
        print("Modo TEST activado")
        test()
    else:
        print("Modo normal")
        roomGenerator()


def test():
    test1()

def test1():
    package = "package com.example.libreria"
    dbname = "Libreria"
    entityList = ["Libro"]

    print("DAO")
    print(generarDAO(package,entityList[0]))
    print("DATABASE")
    print(generarDatabase(package,dbname,entityList))
    print("ENTITY")
    print(generarEntity(package,entityList[0]))
    print("VIEW MODEL")
    print(generarViewModel(package,dbname,entityList[0]))


def roomGenerator():
    package = input("Introduce package: ").strip()
    dbname = input("Introduce nombre de base de datos: ").strip().capitalize()

    print("Inserta nombres de entidad:")
    entityList = solicitar_entidades()

    for entity in entityList:
        createFile(f"{entity}Dao.kt",generarDAO(package,entityList[0]))
        createFile(f"{entity}.kt",generarEntity(package,entityList[0]))


    createFile(f"{dbname}Database.kt",generarDatabase(package,dbname,entityList))
    createFile(f"{dbname}ViewModel.kt",generarViewModel(package,dbname,entityList[0]))
    createFile(f"app.build.gradle.kts",generarBuildGradleApp(package))
    createFile(f"top.build.gradle.kts",generarBuildGradleTop())
    




def createFile(fileName, content):
    # 1. Definimos el nombre de la carpeta
    folder = "output"
    
    # 2. Si la carpeta no existe, la creamos
    if not os.path.exists(folder):
        os.makedirs(folder)
    
    # Combinamos la carpeta con el nombre del archivo (ej: "output/LibroDao.kt")
    filePath = os.path.join(folder, fileName)
    
    try:
        with open(filePath, "w", encoding="utf-8") as file:
            file.write(content.strip())
        print(f"✅ Archivo '{filePath}' generado con éxito.")
    except Exception as e:
        print(f"❌ Error al crear el archivo: {e}")



def solicitar_entidades():
    entidades = []
    while True:
        nombre = input("Introduce el nombre de la entidad (o ENTER para finalizar): ").strip()
        if nombre == "":
            break
        entidades.append(nombre.capitalize())
    return entidades

def generarEntity(package,entityName):
    return '''
{0}

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "{2}")
data class {1}(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)
'''.format(package,entityName,entityName.lower())
    
def generarDAO(package,entityName):
    return '''
{0}

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface {1}Dao {{

    @Query("SELECT * from {2} ORDER BY name ASC")
    fun getAll{1}s(): Flow<List<{1}>>

    @Query("SELECT * from {2} WHERE id = :id")
    fun get{1}(id: Int): Flow<{1}>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: {1})

    @Update()
    suspend fun update(entity: {1})

    @Delete
    suspend fun delete(entity: {1})
}}
    '''.format(package,entityName,entityName.lower())



def generarDatabase(package,databaseName, entities):
    toClassList = lambda entities: "[" + ", ".join(f"{e}::class" for e in entities) + "]"
    prepareDAO = lambda entities: "\n".join([f"    abstract fun {e.lower()}Dao(): {e}Dao" for e in entities])


    return '''
{0}
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = {1}, version = 1, exportSchema = false)
abstract class {3}Database : RoomDatabase() {{
    {2}
    companion object {{
        @Volatile
        private var Instance: {3}Database? = null

        fun getDatabase(context: Context): {3}Database {{
            //context.deleteDatabase("{3}_database")
            return Instance ?: synchronized(this) {{
                Room.databaseBuilder(context,
                    {3}Database::class.java,
                    "{3}_database"
                )
                    .build()
                    .also {{ Instance = it }}
            }}
        }}
    }}
}}
'''.format(package,toClassList(entities),prepareDAO(entities),databaseName)

def generarViewModel(package,databaseName, entity):
    return '''
{0}

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class {1}ViewModel(application: Application) : AndroidViewModel(application) {{

    private val {3}Dao = {1}Database.getDatabase(application).{3}Dao()

    fun insert{2}(name: String, price: Double, quantity: Int) {{
        val new{2} = {2}(name = name, price = price, quantity = quantity)
        Log.d("{1}ViewModel", "Nuevo {2} creado: $new{2}")

        viewModelScope.launch(Dispatchers.IO) {{
            {3}Dao.insert(new{2})
            Log.d("{1}ViewModel", "Insert realizado")
        }}
    }}

    fun get{2}(id: Int): Flow<{2}> {{
        return {3}Dao.get{2}(id)
    }}

    val all{2}s: Flow<List<{2}>> = {3}Dao.getAll{2}s()

    suspend fun delete({3}: {2}){{
        {3}Dao.delete({3})
    }}


    suspend fun update({3}: {2}){{
        {3}Dao.update({3})
    }}

}}
'''.format(package,databaseName,entity,entity.lower())

def generarBuildGradleTop():
    return'''
plugins {
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
    '''
def generarBuildGradleApp(package):
    return'''
plugins {
    id("com.google.devtools.ksp")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "{0}"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "{0}"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    val room_version = "2.8.3"
    implementation("androidx.room:room-runtime:$room_version")
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:$room_version")
    // If this project only uses Java source, use the Java annotationProcessor
    // No additional plugins are necessary
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
    '''.format(package.replace("package ",""))




if __name__ == "__main__":
    main()