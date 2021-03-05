# SimpleCallApi

SimpleCallApi is a library to assist with API calls. The idea is that it would be as simple as possible and could help from the beginner to the most senior developer. SimpleCallApi supports Coroutines and RxJava


### Version
##### Gradle
```
implementation 'com.github.mattyoliveira:simple-call-api:1.0.1'
```

##### Maven
```
<dependency>
  <groupId>com.github.mattyoliveira</groupId>
  <artifactId>simple-call-api</artifactId>
  <version>1.0.1</version>
  <type>aar</type>
</dependency>
```

### Example

##### ApiService
```kotlin
interface Api {
    @GET("/api")
    suspend fun getExemple(): Response<yourType>
}
```

##### NetworkConfig
```kotlin
createClientByService<Api>( baseUrl = "https://yourBase")
`````

##### SimpleRespository
```kotlin
class Exemple(val api: Api): SimpleRepository() {
	suspend fun getExemple() = safeCallApi { api.getExemple() }
}
```

##### SimpleViewModel
```kotlin
class ExempleViewModel(private val useCase: ExempleUseCase): SimpleViewModel() {

	fun getExemple() {
		viewModelScope.launch {
			safeCall { useCase.getExemple() }
				.onSuccess { it as yourType }
				.onError { it as yourType }
	}
}
```

<br/>

### Technology

SimpleCallApi uses the Retrofit2, Coroutines, RxJava2, Moshi and Gson libraries.

### License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
