package com.gildedrose;

/*
 ***PROBLEM DESCRIPTION***
 * Names: 	AgedBrie
 * 			Backstage passes to a TAFKAL90ETC concert
 * 			Sulfuras, Hand of Ragnaros
 * SellIn: días que quedan para venderlo
 * Quality: siempre entre 0 y 50
 * 
 * Aged brie -> aumenta la calidad en una unidad a cada día que pasa
 * 			 -> pasada la fecha de SellIn aumenta la calidad en dos unidades a cada día que pasa
 *
 * Sulfuras  -> su calidad nunca se degrada
 * 
 * Backstage -> pasada la fecha de SellIn la calidad pasa a 0
 * 			 -> 0<SellIn<=5 aumenta en 3 unidades a cada día que pasa
 * 			 -> 5<SellIn<=10 aumenta en 2 unidades a cada día que pasa
*/

/*
 ***INPUT SPECIAL CASES***
 * Negative quality as object creation -> NO PERMITIDO (throw exception en la instanciación)
 * Negative SellIn -> Permitido? Implica que la fecha de venta ya ha pasado
 * Quality > 50 --CUANDO NO ES SULFURAS-- -> NO PERMITIDO
 * Quality < 0 -> NO PERMITIDO
 * Quality != 80 --CUANDO ES SULFURAS-- -> NO PERMITIDO
 * Nombre nulo/longitud menor de x carácteres -> NO PERMITIDO (throw exception en la instanciación)
 * */

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie") && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}