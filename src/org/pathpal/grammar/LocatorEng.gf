concrete LocatorEng of Locator = {
  lincat
    Phrase,Adress,StoreType,Answer,AdressTree= {s : Str} ;    
  lin
    GoTo it = {s = "I need to go to" ++ it.s}  ;
    GoFromTo it it' = {s = "I need to go from" ++ it.s ++ "to" ++ it'.s} ;
    FindStore it = {s = "Where is the nearest" ++ it.s ++ "store"} ;
    
    AdressT i i' = {s = i.s ++ i'.s} ;
    AdressL i = {s = i.s} ;

    WalkOrTrans item = {s = item.s} ;
    
    Walking  = {s = "Walking"} ;
    Transportation = {s = "Transportation"} ;
}
