# JavaDeveloperTask

## Basic functionalities
`POST` to `localhost:8080/meeting` to add a meeting to the system.
The body of the request should look like:
```
{
    "name": "LandMine",                     // Name of the meeting
    "responsiblePerson": "Romas",           // The person responsible for the meeting
    "description": "Nepamirskite kaukiu!",  // The description of the meeting
    "category": "Short",                    // The category of the meeting (fixed: CodeMonkey, Hub, Short, TeamBuilding)
    "type": "InPerson",                     // The type of the meeting (fixed: InPerson, Live)
    "startDate": "2020-02-04",              // The starting date of the meeting
    "endDate": "2020-02-04"                 // The ending date of the meeting
}
```

`DELETE` to `localhost:8080/meeting` to delete a meeting from the system.
The body of the request should look like:
```
{
    "name": "Mine",                         // The name of the meeting to delete
    "sender": "Petras"                      // The name of the user who is initializing the delete request
}
```

`GET` to `localhost:8080/meeting` to filter a meeting by a criteria.
The body of the request should look like:
```
{
    "category":                             // The name of the filter object you want to select ex: description, category, type etc.
    "Short"                                 // The amount or the criteria you want to filter ex: (for catefory -> "Short" or "Hub")
}
```
When filtering by date you can select to filter for each starting date or ending date or you can select both:
```
{
    "startDate":                      
    "2020-02-01"                            
}
```
```
{
    "endDate":                      
    "2020-02-03"                            
}
```
```
{
    "startDate":                      
    "2020-02-01",
    "endDate":
    "2020-02-03"
}
```

`PUT` to `localhost:8080/person` to add a person to a meeting.
The body of the request should look like:
```
{
    "name": "Adomas",                       // The name of the person to add to the meeting
    "date": "2020-02-02"                    // The starting date of the meeting
}
```

`DELETE` to `localhost:8080/person` to remove a person from a meeting.
The body of the request should look like:
```
{
    "meetingName": "Mine",                 // The name of the meeting you want the person to be remove from
    "personName": "Romas"                   // The name of the person you want to be remove from a meeting
}
```
