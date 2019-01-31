<h2>CodersLab - Warsztaty 5 - Spring MVC REST</h2>

Celem warsztatu jest napisanie funkcjonalności backendowej do katalogowania książek metodą REST.

Do stworzenia API wykorzystano Spring MVC, dodatkową bibliotekę Jackson, oraz dodatkowe adnotacje.

Do weryfikacji poprawności stworzonego api można wykorzystać stronę klienta, stworzoną w ramach <a href="https://github.com/marta-krzyzewska/Warsztaty_4">Warsztatów 4</a>

Serwer posiada zaimplementowane ścieżki dostępu:
<ul>
    <li>GET	/books/	Zwraca listę wszystkich książek.</li>
    <li>POST /books/	Tworzy nową książkę na podstawie danych przekazanych z formularza i zapisuje ją do bazy danych.</li>
    <li>GET	/books/{id}	Wyświetla informacje o książce o podanym id.</li>
    <li>PUT	/books/{id}	Zmienia informacje o książce o podanym id na nową.</li>
    <li>DELETE /books/{id}	Usuwa książkę o podanym id z bazy danych.</li>
</ul>