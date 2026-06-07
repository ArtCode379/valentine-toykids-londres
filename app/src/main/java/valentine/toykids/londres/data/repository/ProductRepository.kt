package valentine.toykids.londres.data.repository

import valentine.toykids.londres.data.model.Product
import valentine.toykids.londres.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val products: List<Product> = listOf(
        Product(
            id = 1,
            title = "Wooden Building Blocks Set",
            description = "Premium hardwood building blocks in 40 vibrant shapes and colors. Perfect for developing fine motor skills and spatial reasoning in children aged 2+. Non-toxic paint, sanded smooth for safe play.",
            category = ProductCategory.TOYS,
            price = 24.99,
            imageUrl = "https://images.unsplash.com/photo-1516981879613-9f5da904015f?w=600",
        ),
        Product(
            id = 2,
            title = "Rainbow Stacking Tower",
            description = "Classic rainbow stacking rings in graduated sizes made from natural beech wood. Teaches color recognition, size sequencing, and hand-eye coordination. Suitable for 12 months and up.",
            category = ProductCategory.EDUCATIONAL,
            price = 18.99,
            imageUrl = "https://images.unsplash.com/photo-1611403119860-57c4937ef987?w=600",
        ),
        Product(
            id = 3,
            title = "Soft Bunny Plush Toy",
            description = "Ultra-soft velvet bunny with embroidered features and a crinkle tummy that delights newborns. Hypoallergenic fill, machine washable. Ideal comfort companion from birth.",
            category = ProductCategory.NEWBORN,
            price = 14.99,
            imageUrl = "https://images.unsplash.com/photo-1559087316-b8c0db1e07ae?w=600",
        ),
        Product(
            id = 4,
            title = "Girls Floral Summer Dress",
            description = "Lightweight cotton floral dress with smocked bodice and adjustable straps. Breathable and easy to wash. Available in sizes 2–8 years. Perfect for warm days and special occasions.",
            category = ProductCategory.CLOTHING,
            price = 29.99,
            imageUrl = "https://images.unsplash.com/photo-1607453998774-d533f65dac99?w=600",
        ),
        Product(
            id = 5,
            title = "Number & Shape Puzzle Board",
            description = "Chunky wooden puzzle with 20 number and shape pieces. Each piece features a knob for easy grasping. Introduces numeracy and geometry through play. Ages 2–5.",
            category = ProductCategory.EDUCATIONAL,
            price = 16.99,
            imageUrl = "https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?w=600",
        ),
        Product(
            id = 6,
            title = "Baby Rattle Gift Set",
            description = "Set of 4 colourful rattles in various shapes and textures — ring, dumbell, teether, and maracas. BPA-free, easy-grip design. Stimulates auditory and tactile senses from 3 months.",
            category = ProductCategory.NEWBORN,
            price = 12.99,
            imageUrl = "https://images.unsplash.com/photo-1555252333-9f8e92e65df9?w=600",
        ),
        Product(
            id = 7,
            title = "Wooden Kitchen Play Set",
            description = "28-piece pretend kitchen with stove, sink, utensils, and play food. Encourages imaginative role-play and social skills. Solid MDF construction with rounded edges. Ages 3–7.",
            category = ProductCategory.TOYS,
            price = 49.99,
            imageUrl = "https://images.unsplash.com/photo-1596461404969-9ae70f2830c1?w=600",
        ),
        Product(
            id = 8,
            title = "Boys Nautical Stripe Shirt",
            description = "Classic navy and white striped cotton shirt with anchor embroidery on the chest pocket. Comfortable fit with snap buttons. Machine washable. Sizes 3–10 years.",
            category = ProductCategory.CLOTHING,
            price = 19.99,
            imageUrl = "https://images.unsplash.com/photo-1519238263530-99bdd11df2ea?w=600",
        ),
        Product(
            id = 9,
            title = "Dinosaur Figurines Collection",
            description = "Set of 12 hand-painted dinosaur figurines with realistic detailing — T-Rex, Triceratops, Brachiosaurus, and more. Educational fact cards included. Perfect for collectors ages 4+.",
            category = ProductCategory.TOYS,
            price = 22.99,
            imageUrl = "https://images.unsplash.com/photo-1603807008857-ad66b70431aa?w=600",
        ),
        Product(
            id = 10,
            title = "Girls Hair Accessories Set",
            description = "20-piece set including velvet scrunchies, satin ribbons, pearl clips, and butterfly pins in pastel tones. Gentle on hair, suitable for fine baby hair. Ages 3 and up.",
            category = ProductCategory.ACCESSORIES,
            price = 9.99,
            imageUrl = "https://images.unsplash.com/photo-1596462502278-27bfdc403348?w=600",
        ),
        Product(
            id = 11,
            title = "Baby Sensory Crinkle Ball Set",
            description = "Set of 3 soft sensory balls in contrasting colours with crinkle fabric, jingle bells, and varied textures. Stimulates all five senses. Suitable from birth. Machine washable.",
            category = ProductCategory.NEWBORN,
            price = 15.99,
            imageUrl = "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600",
        ),
        Product(
            id = 12,
            title = "Magnetic Drawing Doodle Board",
            description = "Mess-free magnetic drawing board with colour gradient screen, stamper pen, and erase slider. Hours of creative doodling without paper waste. Reusable. Ages 3–10.",
            category = ProductCategory.EDUCATIONAL,
            price = 21.99,
            imageUrl = "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=600",
        ),
    )

    fun observeById(id: Int): Flow<Product?> {
        val item = products.find { it.id == id }
        return flowOf(item)
    }

    fun getById(id: Int): Product? {
        return products.find { it.id == id }
    }

    fun observeAll(): Flow<List<Product>> {
        return flowOf(products)
    }
}
