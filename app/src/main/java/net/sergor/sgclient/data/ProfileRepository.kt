package net.sergor.sgclient.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class ProfileRepository(context: Context) {
    private val preferences = context.getSharedPreferences(
        "sg_client_android_001",
        Context.MODE_PRIVATE,
    )

    fun loadProfiles(): List<ClientProfile> {
        val source = preferences.getString(KEY_PROFILES, null) ?: return emptyList()

        return runCatching {
            val array = JSONArray(source)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.getJSONObject(index)
                    val type = runCatching {
                        ProfileType.valueOf(item.getString("type"))
                    }.getOrNull() ?: continue

                    add(
                        ClientProfile(
                            id = item.getString("id"),
                            name = item.getString("name"),
                            type = type,
                            endpoint = item.getString("endpoint"),
                            importedAtEpochSeconds =
                                item.optLong("importedAtEpochSeconds", 0L),
                        ),
                    )
                }
            }
        }.getOrElse { emptyList() }
    }

    fun saveProfiles(profiles: List<ClientProfile>) {
        val array = JSONArray()
        profiles.forEach { profile ->
            array.put(
                JSONObject()
                    .put("id", profile.id)
                    .put("name", profile.name)
                    .put("type", profile.type.name)
                    .put("endpoint", profile.endpoint)
                    .put(
                        "importedAtEpochSeconds",
                        profile.importedAtEpochSeconds,
                    ),
            )
        }

        preferences.edit()
            .putString(KEY_PROFILES, array.toString())
            .apply()
    }

    fun loadSelectedProfileId(): String? =
        preferences.getString(KEY_SELECTED_PROFILE, null)

    fun saveSelectedProfileId(profileId: String?) {
        preferences.edit()
            .putString(KEY_SELECTED_PROFILE, profileId)
            .apply()
    }

    fun loadThemeMode(): ThemeMode {
        val saved = preferences.getString(KEY_THEME, ThemeMode.SYSTEM.name)
        return runCatching { ThemeMode.valueOf(saved.orEmpty()) }
            .getOrDefault(ThemeMode.SYSTEM)
    }

    fun saveThemeMode(mode: ThemeMode) {
        preferences.edit()
            .putString(KEY_THEME, mode.name)
            .apply()
    }

    private companion object {
        const val KEY_PROFILES = "profiles"
        const val KEY_SELECTED_PROFILE = "selected_profile"
        const val KEY_THEME = "theme"
    }
}
