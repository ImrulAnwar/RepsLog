package com.imrul.replog.di

import android.content.Context
import androidx.room.Room
import com.imrul.replog.core.Constants.MEASUREMENT_DATABASE_NAME
import com.imrul.replog.feature_measurements.data.data_source.MeasurementDao
import com.imrul.replog.feature_measurements.data.data_source.MeasurementDatabase
import com.imrul.replog.feature_measurements.data.repository.MeasurementRepositoryImpl
import com.imrul.replog.feature_measurements.domain.repository.MeasurementRepository
import com.imrul.replog.feature_measurements.domain.use_cases.DeleteMeasurement
import com.imrul.replog.feature_measurements.domain.use_cases.GetAllMeasurementsByCategory
import com.imrul.replog.feature_measurements.domain.use_cases.MeasurementUseCases
import com.imrul.replog.feature_measurements.domain.use_cases.UpsertMeasurement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MeasurementRoomModule {
    @Singleton
    @Provides
    fun provideMeasurementDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, MeasurementDatabase::class.java, MEASUREMENT_DATABASE_NAME)
        .build()

    @Singleton
    @Provides
    fun provideMeasurementDao(
        database: MeasurementDatabase
    ) = database.measurementDao()

    @Singleton
    @Provides
    fun provideMeasurementRepository(dao: MeasurementDao): MeasurementRepository =
        MeasurementRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideMeasurementUseCases(repository: MeasurementRepository) = MeasurementUseCases(
        upsertMeasurement = UpsertMeasurement(repository),
        deleteMeasurement = DeleteMeasurement(repository),
        getAllMeasurementsByCategory = GetAllMeasurementsByCategory(repository)
    )
}