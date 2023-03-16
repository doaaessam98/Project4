package com.udacity.project4.locationreminders.reminderslist

import com.udacity.project4.data.local.LocalDataSource
import com.udacity.project4.domain.model.ReminderDTO
import com.udacity.project4.base.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource (var reminders: MutableList<ReminderDTO> = mutableListOf()):
    LocalDataSource {

//    TODO: Create a fake data source to act as a double to the real data source
    private var shouldReturnError = false

        fun setReturnError(shouldReturnError: Boolean) {
            this.shouldReturnError = shouldReturnError
        }
    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        reminders.let { return Result.Success(ArrayList(it)) }
        return Result.Error(" not found")

    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        if (shouldReturnError) {
            return Result.Error("Error while getting reminder with id: $id")
        }
        return try {
            Result.Success(reminders.first { it.id == id })
        } catch (ex: Exception) {
            Result.Error("Reminder not found")
        }
    }

    override suspend fun deleteAllReminders() {
        reminders.clear()
    }


}