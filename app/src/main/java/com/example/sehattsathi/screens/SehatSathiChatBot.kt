//package com.example.sehattsathi.screens
//
//import android.net.Uri
//import android.provider.OpenableColumns
//import android.text.format.Formatter
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.widthIn
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyListState
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Attachment
//import androidx.compose.material.icons.filled.Send
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import com.example.sehattsathi.viewmodel.MyViewModel.Attachement
//import com.example.sehattsathi.viewmodel.MyViewModel.firebaseAiLogicChatViewModel
//import com.google.firebase.ai.type.Content
//import com.google.firebase.ai.type.ImagePart
//import com.google.firebase.ai.type.TextPart
//import kotlinx.coroutines.launch
//
//
//// 🎨 Custom colors
//private val BubbleUser = Color(0xFF0084C5)     // User messages (blue)
//private val BubbleModel = Color(0xFFFFFFFF)    // Model messages (white card)
//private val Background = Color(0xFFD6E8F4)     // Screen background (light blue)
//
//@Composable
//fun chatBot(
//    chatViewModel: firebaseAiLogicChatViewModel = viewModel()
//) {
//    val context = LocalContext.current
//    val messages by chatViewModel.messages.collectAsStateWithLifecycle()
//    val isLoading by chatViewModel.isLoading.collectAsStateWithLifecycle()
//    val errorMessage by chatViewModel.errorMessage.collectAsStateWithLifecycle()
//    val attachments by chatViewModel.attachement.collectAsStateWithLifecycle()
//
//    val listState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Background)
//    ) {
//        AsyncImage(
//            model = "https://i.postimg.cc/QNHmjhBF/sehatsathioriginal-removebg-preview.png",
//            contentDescription = "Remote Image",
//            modifier = Modifier.size(270.dp),
//            alignment = Alignment.Center
//        )
//
//        Spacer(modifier = Modifier.height(5.dp))
//
//        ChatList(
//            messages = messages,
//            attachments = attachments,
//            listState = listState,
//            modifier = Modifier.weight(1f)
//        )
//
//        Divider(color = BubbleUser.copy(alpha = 0.3f))
//
//        ChatInputSection(
//            isLoading = isLoading,
//            onSendMessage = {
//                chatViewModel.sendMessage(it)
//                coroutineScope.launch {
//                    listState.animateScrollToItem(messages.size + 1)
//                }
//            },
//            onFileAttached = { uri ->
//                handleAttachUri(context, uri, chatViewModel)
//            }
//        )
//
//        errorMessage?.let {
//            Text(
//                text = it,
//                color = Color.Red,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
//    }
//}
//
//private fun handleAttachUri(
//    context: android.content.Context,
//    uri: Uri,
//    chatViewModel: firebaseAiLogicChatViewModel
//) {
//    val contentResolver = context.contentResolver
//    val mimeType = contentResolver.getType(uri).orEmpty()
//    var fileName: String? = null
//
//    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
//        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
//        if (cursor.moveToFirst() && nameIndex != -1 && sizeIndex != -1) {
//            val sizeBytes = cursor.getLong(sizeIndex)
//            val humanSize = Formatter.formatShortFileSize(context, sizeBytes)
//            val name = cursor.getString(nameIndex)
//            fileName = "$name ($humanSize)"
//        }
//    }
//
//    contentResolver.openInputStream(uri)?.use { stream ->
//        val bytes = stream.readBytes()
//        chatViewModel.addAttachement(bytes, mimeType, fileName)
//    }
//}
//
//@Composable
//fun ChatList(
//    messages: List<Content>,
//    attachments: List<Attachement>,
//    listState: LazyListState,
//    modifier: Modifier = Modifier
//) {
//    LazyColumn(
//        state = listState,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        reverseLayout = false
//    ) {
//        items(messages) { message ->
//            ChatBubble(message)
//        }
//
//        items(attachments) { attachment ->
//            attachment.fileName?.let {
//                Text(
//                    text = "📎 $it (Pending)",
//                    modifier = Modifier.padding(8.dp),
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.DarkGray
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ChatBubble(chatMessage: Content) {
//    val isFromModel = chatMessage.role == "model"
//    val bubbleColor = if (isFromModel) BubbleModel else BubbleUser
//    val textColor = if (isFromModel) Color.Black else Color.White
//    val shape = if (isFromModel) {
//        RoundedCornerShape(12.dp, 12.dp, 12.dp, 0.dp)
//    } else {
//        RoundedCornerShape(12.dp, 12.dp, 0.dp, 12.dp)
//    }
//
//    Row(
//        horizontalArrangement = if (isFromModel) Arrangement.Start else Arrangement.End,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//    ) {
//        Card(
//            shape = shape,
//            elevation = CardDefaults.cardElevation(defaultElevation = if (isFromModel) 4.dp else 0.dp),
//            colors = CardDefaults.cardColors(containerColor = bubbleColor),
//            modifier = Modifier
//                .widthIn(max = 300.dp)
//                .padding(4.dp)
//        ) {
//            Column(modifier = Modifier.padding(12.dp)) {
//                chatMessage.parts.forEach { part ->
//                    when (part) {
//                        is TextPart -> {
//                            Text(text = part.text, color = textColor)
//                        }
//                        is ImagePart -> {
//                            Image(
//                                bitmap = part.image.asImageBitmap(),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(top = 4.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ChatInputSection(
//    isLoading: Boolean,
//    onSendMessage: (String) -> Unit,
//    onFileAttached: (Uri) -> Unit
//) {
//    var message by remember { mutableStateOf(TextFieldValue("")) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let { onFileAttached(it) }
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        OutlinedTextField(
//            value = message,
//            onValueChange = { message = it },
//            label = { Text("Type a message") },
//            modifier = Modifier
//                .weight(1f)
//                .padding(end = 8.dp),
//            maxLines = 4,
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = BubbleUser,
//                focusedLabelColor = BubbleUser,
//                focusedTextColor = BubbleUser,
//                cursorColor = BubbleUser,
//                unfocusedBorderColor = Color.Gray,
//                unfocusedLabelColor = Color.Gray,
//                unfocusedTextColor = Color.Black
//            )
//        )
//
//        IconButton(
//            onClick = { launcher.launch("image/*") },
//            modifier = Modifier
//                .clip(CircleShape)
//                .background(BubbleModel)
//        ) {
//            Icon(imageVector = Icons.Default.Attachment, contentDescription = "Attach")
//        }
//
//        IconButton(
//            onClick = {
//                if (message.text.isNotBlank()) {
//                    onSendMessage(message.text)
//                    message = TextFieldValue("")
//                }
//            },
//            enabled = !isLoading,
//            modifier = Modifier
//                .clip(CircleShape)
//                .background(BubbleUser)
//                .size(48.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Send,
//                contentDescription = "Send",
//                tint = Color.White
//            )
//        }
//    }
//}
