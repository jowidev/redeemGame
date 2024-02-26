# Client events
_Eventos enviados a el servidor_ 

| Evento  | Descripcion                | Data       |
|---------|----------------------------|------------|
| connect | Conecta al servidor        |            |
| plant*  | Plantar un slime o boulder | cell#tropa |

_*Se comprueba que el dinero sea suficiente, en caso de no serlo la tropa no será plantada_

# Server events
_Eventos enviados al cliente_

| Evento    | Descripcion                   | Data       | Types           |
|-----------|-------------------------------|------------|-----------------|
| connected | Conectado a el cliente        | idCliente  | int             |
| ready     | Juego comenzado               |            |                 |
| planted   | Tropa plantada                | cell#tropa | int#int         |
| money     | Plata actual del jugador      | plata      | float           |
| over      | Juego terminado               | idGanador  | int             |


