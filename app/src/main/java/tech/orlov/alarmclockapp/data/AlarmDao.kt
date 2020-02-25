package tech.orlov.alarmclockapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarmdbo")
    fun getAllAlarms(): Maybe<List<AlarmDbo>>

    @Query("SELECT * FROM alarmdbo WHERE id = :id")
    fun getAlarmById(id: Long): Maybe<AlarmDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlarm(alarm: AlarmDbo): Maybe<Long>

    @Query("DELETE FROM alarmdbo WHERE id = :id")
    fun deleteAlarm(id: Long): Completable
}