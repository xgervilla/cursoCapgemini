package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;

/*
 ***INPUT SPECIAL CASES***
 * Negative quality as object creation -> NO PERMITIDO (throw exception en la instanciación)
 * Negative SellIn -> Permitido? Implica que la fecha de venta ya ha pasado
 * Quality > 50 --CUANDO NO ES SULFURAS-- -> NO PERMITIDO
 * Quality < 0 -> NO PERMITIDO
 * Quality != 80 --CUANDO ES SULFURAS-- -> NO PERMITIDO
 * Nombre nulo/longitud menor de x carácteres -> NO PERMITIDO (throw exception en la instanciación)
 * */

class GildedRoseTest {

	@Test
	@Disabled
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }
    
    @Nested
    class OK {
    	
    	//Test con el Item Sulfuras --> tanto SellIn como la calidad se mantienen sin importar el paso del tiempo (SellIn es potencialmente "inutil")
    	@ParameterizedTest(name = "SellIn: {0}, Quality: {1}, SellInFinal: {2}, QualityFinal: {3}")
    	@Disabled
    	@CsvSource(value = {"0,80","5,80"})
        void SulfurasTest(int sellIn, int quality) {
            Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", sellIn, quality) };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name);
            assertEquals(sellIn, app.items[0].sellIn);
            assertEquals(quality, app.items[0].quality);
        }
        
    	//Test con el Item Aged Brie --> 
    	@ParameterizedTest(name = "SellIn: {0}, Quality: {1}, SellInFinal: {2}, QualityFinal: {3}")
        @CsvSource(value = {"0,5,7", "6,50,50", "-1,49,50"})
        void BrieTest(int sellIn, int qualityInitial, int qualityFinal) {
            Item[] items = new Item[] { new Item("Aged Brie", sellIn, qualityInitial) };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals("Aged Brie", app.items[0].name);
            assertEquals(sellIn-1, app.items[0].sellIn);
            assertEquals(qualityFinal, app.items[0].quality);
        }
        
        //Test con el Item Backstage passes
    	@ParameterizedTest(name = "SellIn: {0}, Quality: {1}, SellInFinal: {2}, QualityFinal: {3}")
        @Disabled
        @CsvSource(value = {"0,0,0,0"})
        void BackstageTest(int sellInInitial, int qualityInitial, int sellInFinal, int qualityFinal) {
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", sellInInitial, qualityInitial) };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
            assertEquals(sellInFinal, app.items[0].sellIn);
            assertEquals(qualityFinal, app.items[0].quality);
        }
    }
    
    class KO {
    	
    	//Test con el Item Sulfuras
    	@CsvSource(value = {"0,50,0,83","0,-4,0,80"})
        void SulfurasTestInvalid(int sellInInitial, int qualityInitial, int sellInFinal, int qualityFinal) {
            Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", sellInInitial, qualityInitial) };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name);
            assertEquals(sellInFinal, app.items[0].sellIn);
            assertEquals(qualityFinal, app.items[0].quality);
        }
        
    	//Test con el Item Aged Brie
        @Test
        @CsvSource(value = {"0,0,0,0"})
        void BrieTestInvalid(int sellInInitial, int qualityInitial, int sellInFinal, int qualityFinal) {
            Item[] items = new Item[] { new Item("Aged Brie", sellInInitial, qualityInitial) };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals("Aged Brie", app.items[0].name);
            assertEquals(sellInFinal, app.items[0].sellIn);
            assertEquals(qualityFinal, app.items[0].quality);
        }
        
        //Test con el Item Backstage passes
        @Test
        @CsvSource(value = {"0,0,0,0"})
        void BackstageTestInvalid(int sellInInitial, int qualityInitial, int sellInFinal, int qualityFinal) {
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", sellInInitial, qualityInitial) };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
            assertEquals(sellInFinal, app.items[0].sellIn);
            assertEquals(qualityFinal, app.items[0].quality);
        }
    }

}
