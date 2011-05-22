abstract Locator = {
  flags startcat = Phrase ;
  cat
    Phrase;
    Dummy;
    StoreType;
    Answer;
    TransItem;
    WalkItem;
    NeedWant;
    GoItem;
  fun
    AndThen : GoItem -> Phrase; 
    INeedWantTo : NeedWant -> GoItem -> Phrase; 
    GoTo : Dummy -> GoItem ;
    GoByCarTo : Dummy -> GoItem ;
    GoByCarFromTo : Dummy -> Dummy -> GoItem ;
    GoToVia : Dummy -> Dummy -> GoItem; 
    GoFromToVia : Dummy -> Dummy -> Dummy -> GoItem;
    WalkTo : Dummy -> GoItem;
    DString : Dummy ;
    GoFromTo :  Dummy -> Dummy -> Phrase;
    FindStore : StoreType -> Phrase ; 
    WalkOrTrans : Answer -> Phrase;
    Walking : WalkItem -> Answer;
    Transportation : TransItem -> Answer;
    Need: NeedWant;
    Want: NeedWant;
    Walk,Walk2,Foot : WalkItem;
    Car,Vehicle : TransItem;
    TBy: TransItem -> TransItem;
}
