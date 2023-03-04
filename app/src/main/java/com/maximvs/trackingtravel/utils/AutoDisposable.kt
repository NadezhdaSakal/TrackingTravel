package com.maximvs.trackingtravel.utils

/*

class AutoDisposable : DefaultLifecycleObserver {

//Наследуем от LifecycleObserver

    //Используем CompositeDisposable для отмены всех Observable
    lateinit var compositeDisposable: CompositeDisposable

    //Сюда передаем ссылку на ЖЦ компонента, за которым будет слежение
    fun bindTo(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        compositeDisposable = CompositeDisposable()
    }

    //Метод для добавления Observable в CompositeDisposable
    fun add(disposable: Disposable) {
        if (::compositeDisposable.isInitialized) {
            compositeDisposable.add(disposable)
        } else {
            throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.dispose()
    }

}

//Экстеншн
fun Disposable.addTo(autoDisposable: AutoDisposable) {
    autoDisposable.add(this)
}

*/