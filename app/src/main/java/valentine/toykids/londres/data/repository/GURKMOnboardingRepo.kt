package valentine.toykids.londres.data.repository

import valentine.toykids.londres.data.datastore.GURKMOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GURKMOnboardingRepo(
    private val gurkmOnboardingStoreManager: GURKMOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return gurkmOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            gurkmOnboardingStoreManager.setOnboardedState(state)
        }
    }
}