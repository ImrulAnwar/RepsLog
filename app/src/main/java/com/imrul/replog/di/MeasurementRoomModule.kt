package com.imrul.replog.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imrul.replog.core.Constants.MEASUREMENT_DATABASE_NAME
import com.imrul.replog.feature_measurements.data.data_source.MeasurementDao
import com.imrul.replog.feature_measurements.data.data_source.MeasurementDatabase
import com.imrul.replog.feature_measurements.data.repository.MeasurementRepositoryImpl
import com.imrul.replog.feature_measurements.data.repository.MeasurementsDataSourceImpl
import com.imrul.replog.feature_measurements.domain.repository.MeasurementRepository
import com.imrul.replog.feature_measurements.domain.repository.MeasurementsDataSource
import com.imrul.replog.feature_measurements.domain.use_cases.DeleteMeasurement
import com.imrul.replog.feature_measurements.domain.use_cases.GetAllMeasurementsByCategory
import com.imrul.replog.feature_measurements.domain.use_cases.GetMeasurementById
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

    @Provides
    @Singleton
    fun provideMeasurementsDataSource(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): MeasurementsDataSource = MeasurementsDataSourceImpl(auth, firestore)

    @Singleton
    @Provides
    fun provideMeasurementRepository(dao: MeasurementDao, dataSource: MeasurementsDataSource): MeasurementRepository =
        MeasurementRepositoryImpl(dao, dataSource)

    @Singleton
    @Provides
    fun provideMeasurementUseCases(repository: MeasurementRepository) = MeasurementUseCases(
        upsertMeasurement = UpsertMeasurement(repository),
        deleteMeasurement = DeleteMeasurement(repository),
        getAllMeasurementsByCategory = GetAllMeasurementsByCategory(repository),
        getMeasurementById = GetMeasurementById(repository)
    )
}