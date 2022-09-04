package OrderService.test.controller;

import OrderService.test.domain.item.Food;
import OrderService.test.domain.item.Item;
import OrderService.test.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new FoodForm());
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String create(FoodForm form) {
        Food food = new Food();
        food.setName(form.getName());
        food.setPrice(form.getPrice());
        food.setStockQuantity(form.getStockQuantity());
        food.setDeliveryRangeLimit(form.getDeliveryRangeLimit());
        food.setFoodCode(form.getFoodCode());

        itemService.saveItem(food);
        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping(value = "items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Food item = (Food) itemService.findOne(itemId);

        FoodForm form = new FoodForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setDeliveryRangeLimit(item.getDeliveryRangeLimit());
        form.setFoodCode(item.getFoodCode());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping(value = "items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") FoodForm form) {

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }


}
