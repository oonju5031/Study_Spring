package hello.springMVC1_itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // Given
        Item item = new Item("itemA", 10000, 10);

        // When
        Item savedItem = itemRepository.save(item);

        // Then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(savedItem).isEqualTo(findItem);
    }

    @Test
    void findAll() {
        // Given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 15000, 3);

        itemRepository.save(item1);
        itemRepository.save(item2);

        // When
        List<Item> result = itemRepository.findAll();

        // Then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }

    @Test
    void update() {
        // Given
        Item item1 = new Item("item1", 10000, 10);

        Item savedItem = itemRepository.save(item1);
        Long savedId = savedItem.getId();

        // When
        Item item2 = new Item("item2", 15000, 3);
        itemRepository.update(savedId, item2);

        // Then
        assertThat(item1.getItemName()).isEqualTo(item2.getItemName());
        assertThat(item1.getPrice()).isEqualTo(item2.getPrice());
        assertThat(item1.getQuantity()).isEqualTo(item2.getQuantity());
    }
}