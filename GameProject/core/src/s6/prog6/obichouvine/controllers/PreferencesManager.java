package s6.prog6.obichouvine.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Handles the game preferences.
 */
public class PreferencesManager
{
	// constants
	private static final String PREF_PSEUDO_1 = "pseudo";
	private static final String PREF_PSEUDO_2 = "pseudo2";
	private static final String PREF_VOLUME = "volume";
	private static final String PREF_MUSIC_ENABLED = "music.enabled";
	private static final String PREF_SOUND_ENABLED = "sound.enabled";
	private static final String PREFS_NAME = "Obichouvine";

	public PreferencesManager()
	{
	}

	protected Preferences getPrefs()
	{
		return Gdx.app.getPreferences(PREFS_NAME);
	}

	public boolean isSoundEnabled()
	{
		return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
	}

	public void setSoundEnabled(
			boolean soundEffectsEnabled )
	{
		getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
		getPrefs().flush();
	}

	public boolean isMusicEnabled()
	{
		return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
	}

	public void setMusicEnabled(
			boolean musicEnabled )
	{
		getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
		getPrefs().flush();
	}

	public float getVolume()
	{
		return getPrefs().getFloat(PREF_VOLUME, 0.5f);
	}

	public void setVolume(
			float volume )
	{
		getPrefs().putFloat( PREF_VOLUME, volume );
		getPrefs().flush();
	}

	public String getPseudo()
	{
		return getPrefs().getString(PREF_PSEUDO_1, "Unnamed");
	}

	public void setPseudo(
			String pseudo )
	{
		getPrefs().putString(PREF_PSEUDO_1, pseudo);
		getPrefs().flush();
	}
	
	public String getPseudoP2()
	{
		return getPrefs().getString(PREF_PSEUDO_2, "Unnamed2");
	}

	public void setPseudoP2(
			String pseudo )
	{
		getPrefs().putString(PREF_PSEUDO_2, pseudo);
		getPrefs().flush();
	}
}