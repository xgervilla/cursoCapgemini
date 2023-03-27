package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;

/*
 ***INPUT SPECIAL CASES***
 * Negative quality as object creation -> NO PERMITIDO (throw exception en la instanciación)
 * Negative SellIn -> Permitido, indica que la fecha de venta ya ha pasado
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
    	@ParameterizedTest(name = "SellIn: {0}, Quality: {1}, QualityFinal: {2}")
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
        
    	//Test con el Item Aged Brie --> sellIn decrementa en 1 y la calidad aumenta según las especificaciones
    	@ParameterizedTest(name = "SellIn: {0}, Quality: {1}, QualityFinal: {2}")        
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
    	@ParameterizedTest(name = "SellIn: {0}, Quality: {1}, QualityFinal: {2}")
        @Disabled
        @CsvSource(value = {"0,14,0", "20,15,16", "7,0,2","2,46,49","1,49,50"})
        void BackstageTest(int sellIn, int qualityInitial, int qualityFinal) {
            Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, qualityInitial) };
            GildedRose app = new GildedRose(items);
            app.updateQuality();
            assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
            assertEquals(sellIn-1, app.items[0].sellIn);
            assertEquals(qualityFinal, app.items[0].quality);
        }
    }
    
    @Nested
    class KO {
    	
    	/*
    	 * Invalid quality:
    	 	* Valor nulo?
    	 	* Quality < 0 || Quality > 50 -> NO PERMITIDO	--excepto el special case--
    	 	* Special case: Sulfuras. Quality != 80 -> NUNCA PERMITIDO
		 * Invalid name:
		 	* Nombre nulo/longitud menor de x carácteres -> NO PERMITIDO (throw exception en la instanciación)
	 	* Valid SellIn:
	 		* Valor nulo?
    	*/
    	
    	//la calidad debe estar entre 0 y 50 (ambos incluidos) -- Siempre que el item NO sea Sulfuras
    	@Disabled
    	@ParameterizedTest(name = "Item: {0}, quality: {1}")
    	@CsvSource(value= {"'Sulfuras, Hand of Ragnaros',-3","Aged Brie,-9","Backstage passes to a TAFKAL80ETC concert,-1", "Aged Brie,53","Backstage passes to a TAFKAL80ETC concert,51"})
    	void QualityBetweenLimits(String itemName, int quality) {
    		if (!"Sulfuras, Hand of Ragnaros".equals(itemName)) {
    			assertAll("Quality in limits",
    					() -> assertFalse(quality<0, "Quality below zero"),
    					() -> assertFalse(quality>50, "Quality above 50"));
    		}
    	}
    	
    	//la calidad del item Sulfuras siempre vale 80
    	@Disabled
    	@ParameterizedTest(name = "Item: {0}, quality: {1}")
    	@CsvSource(value= {"'Sulfuras, Hand of Ragnaros',-3","Aged Brie,-9","Backstage passes to a TAFKAL80ETC concert,-1"})
    	void QualitySulfuras(String itemName, int quality) {
    		if ("Sulfuras, Hand of Ragnaros".equals(itemName)) {
    			assertAll("Quality in limits",
    					() -> assertFalse(quality!=80, "Quality equals 80"));
    		}
    	}
    	
    	//la calidad debe estar entre 0 y 50 (ambos incluidos) -- Siempre que el item NO sea Sulfuras, en cuyo caso siempre debe valer 80
    	@ParameterizedTest(name = "Item: {0}, quality: {1}")
    	@CsvSource(value= {"'Sulfuras, Hand of Ragnaros',-3","Aged Brie,-9","Backstage passes to a TAFKAL80ETC concert,-1", "Aged Brie,53","Backstage passes to a TAFKAL80ETC concert,51"})
    	void validQualityInLimits(String itemName, int quality) {
    		if (!"Sulfuras, Hand of Ragnaros".equals(itemName)) {
    			assertAll("Quality in limits",
    					() -> assertFalse(quality<0, "Quality below zero"),
    					() -> assertFalse(quality>50, "Quality above 50"));
    		}
    		else {
    			assertFalse(quality!=80, "Quality equals 80");
    		}
    	}
    	
    	//Test con el Item Sulfuras
    	@Disabled
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
    	@Disabled
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
        @Disabled
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
