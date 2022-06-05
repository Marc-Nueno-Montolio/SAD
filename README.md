# Software per Aplicacions Distribuïdes
En aquest repositori es poden trobar les pràctiques de l'assignatura SAD realitzades per Marina Morgado i Marc Nueno el 2022.

S'han desenvolupat 3 pràctiques en Java i 1 projecte en Python.
Pràctiques en Java:
- **EditableBufferedReader**. Aplicació de consola per editar text en raw mode.t  
- **Client textual**. Aplicació Client/Servidor d'un Xat. 
- **Client gràfic amb Swing**. Aplicació Client/Servidor d'un Xat. .

Projecte Final:
- **SAD Project**. Una aplicació web desenvolupada en Python fent servir el patró  MVC per facilitar la reserva de material esportiu.
## EditableBufferedReader
És un miniprojecte en el que es treballa el patró MVC, parsing de seqüències d'escape d'un emulador de terminal i execució de programes externs des de JAVA.

S'han desenvolupat varies versions:
### Versió noMVC
Primera versió del programa, que permet introduïr una línea de text en mode raw i permet la seva edició utilitzant tecles com endavant, enrere, fi, suprimir... Fa servir parsing de seqüències d'escapament.
[Veure codi](https://github.com/Marc-Nueno-Montolio/SAD/tree/main/EditableBufferedReader/noMVC)

### Versió MVC
En aquesta versió s'ha implementat el patrò MVC fent servir Observer/Observa le i s'ha fet una refactorització del codi.
[Veure codi](https://github.com/Marc-Nueno-Montolio/SAD/tree/main/EditableBufferedReader/MVC)

### Versió RatolíMVC
En aquesta versió, a més de tota la funcionalitat de la versió MVC, s'ha afegit la possibilitat de situar-se a qualsevol lloc fent servir el cursor del ratolí. S'han fet servir seqüències d'escapament Xterm.
[Veure codi](https://github.com/Marc-Nueno-Montolio/SAD/tree/main/EditableBufferedReader/RatoliMVC)

## Client textual (NetworkChat)
Consisteix en el disseny del client i servidor d'un programa de Xat. 

S'ha desenvolupat un client textual per a terminal i el seu corresponent servidor de Chat (Server), que es comuniquen mitajnçant sockets.

Al servidor s'han fet diverses implementacions de l'interfície Server.java:
- ConcurrentHashMapImpl.java
- HashMapMonitor.java
- ReentrantReadWriteLockImpl.java
- SynchronizedMapImpl.java
D'aquesta manera, en temps d'execució es pot escollir quin tipus de servidor es vol desplegar fent servir la classe RunServer.java
[Veure codi servidor](https://github.com/Marc-Nueno-Montolio/SAD/tree/main/NetworkChat/server)
[Veure codi client](https://github.com/Marc-Nueno-Montolio/SAD/blob/main/NetworkChat/Client.java)

## Client gràfic amb Swing (NetworkChatSwingClient)
S'ha implementat el client de xat fent servir la biblioteca gràfica Swing.
[Veure codi client](https://github.com/Marc-Nueno-Montolio/SAD/blob/main/NetworkChatSwingClient/Client.java)

## ProjecteFinal (SADProject)
S'ha desenvolupat una aplicació Web basada en el patró MVC amb l'objectiu de simplificar el lloguer de material esportiu.

L'aplicació s'ha desenvolupat fent servir els frameworks Flask, Bootstrap i JQuery.
[Veure codi](https://github.com/Marc-Nueno-Montolio/SAD/tree/main/ProjecteFinal)
