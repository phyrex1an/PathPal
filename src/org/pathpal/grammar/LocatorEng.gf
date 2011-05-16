concrete LocatorEng of Locator = {
  lincat
    Phrase,StoreType,Answer,Dummy= {s : Str} ;    
  lin
    GoTo it = {s = "I need to go to" ++ it.s}  ;
    GoFromTo it it' = {s = "I need to go from" ++ it.s ++ "to" ++ it'.s} ;
    FindStore it = {s = "Where is the nearest" ++ it.s ++ "store"} ;
    
    DString = {s = "dummy"} ;

    WalkOrTrans item = {s = item.s} ;
    
    Walking  = {s = "Walking"} ;
    Transportation = {s = "Transportation"} ;
}
