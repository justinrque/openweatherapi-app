# OpenWeatherAPI Android App
This application uses the OpenWeather API.

## Guide
You must use your own API Key to make the app work, you may need to register for your own public key.


Paste your API key in the **Data.kt** located in **com.android.que.openweatherapi**

```bash
class Data {
    companion object {
        const val API_KEY = "INPUT YOUR API KEY HERE"
        const val BASE_URL = "https://api.openweathermap.org/"
        const val CITY_NAME = "Manila"
    }
}
```

**Voila! The app should now work.**

-EOF-