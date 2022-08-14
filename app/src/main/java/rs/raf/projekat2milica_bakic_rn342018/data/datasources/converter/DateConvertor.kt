package rs.raf.projekat2milica_bakic_rn342018.data.datasources.converter

import androidx.room.TypeConverter
import org.koin.core.KoinComponent
import java.util.*

class DateConvertor: KoinComponent {

    @TypeConverter
    fun fromBeautifyString(value: String): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToBeautifyString(date: Date): String {
        return date.toString()
    }

}