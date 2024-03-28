package com.cs4520.assignment5

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ItemUI(product: Product) {
    val backgroundColor = if (product.type == "Food") Color(0xFFFFD965) else Color(0xFFE06666)
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(color = backgroundColor)

    ) {
        Image(
            modifier = Modifier.size(72.dp),
            painter = painterResource(id = if (product.type == "Food") R.drawable.food else R.drawable.equipment),
            contentDescription = "Product Image")

        Column() {
            Text(text = product.name)
            product.expiryDate?.let { Text(text = it) }
            Text(text = product.price.toString())
        } }
}



@Preview
@Composable
fun ItemUIPreview() {
    ItemUI( Product(
        name = "Product Name",
        type = "Food",
        expiryDate = "Expiry Date",
        price = 10.99
    ))
}
