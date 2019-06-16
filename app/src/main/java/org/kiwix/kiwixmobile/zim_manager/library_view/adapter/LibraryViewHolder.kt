package org.kiwix.kiwixmobile.zim_manager.library_view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.library_divider.divider_text
import kotlinx.android.synthetic.main.library_item.creator
import kotlinx.android.synthetic.main.library_item.date
import kotlinx.android.synthetic.main.library_item.description
import kotlinx.android.synthetic.main.library_item.favicon
import kotlinx.android.synthetic.main.library_item.fileName
import kotlinx.android.synthetic.main.library_item.language
import kotlinx.android.synthetic.main.library_item.publisher
import kotlinx.android.synthetic.main.library_item.size
import kotlinx.android.synthetic.main.library_item.title
import org.kiwix.kiwixmobile.KiwixApplication
import org.kiwix.kiwixmobile.downloader.model.Base64String
import org.kiwix.kiwixmobile.extensions.setBitmap
import org.kiwix.kiwixmobile.extensions.setTextAndVisibility
import org.kiwix.kiwixmobile.utils.BookUtils
import org.kiwix.kiwixmobile.utils.NetworkUtils
import org.kiwix.kiwixmobile.zim_manager.library_view.adapter.LibraryListItem.BookItem
import org.kiwix.kiwixmobile.zim_manager.library_view.adapter.LibraryListItem.DividerItem

sealed class LibraryViewHolder<T : LibraryListItem>(override val containerView: View) : RecyclerView.ViewHolder(
    containerView
), LayoutContainer {

  abstract fun bind(item: T)

  class LibraryBookViewHolder(
    view: View,
    private val bookUtils: BookUtils,
    private val clickAction: (BookItem) -> Unit
  ) : LibraryViewHolder<BookItem>(view) {
    override fun bind(item: BookItem) {
      title.setTextAndVisibility(item.book.title)
      description.setTextAndVisibility(item.book.description)
      creator.setTextAndVisibility(item.book.creator)
      publisher.setTextAndVisibility(item.book.publisher)
      date.setTextAndVisibility(item.book.date)
      size.setTextAndVisibility(KiloByte(item.book.size).humanReadable)
      language.text = bookUtils.getLanguage(item.book.getLanguage())
      fileName.text = NetworkUtils.parseURL(
          KiwixApplication.instance, item.book.url
      )
      favicon.setBitmap(Base64String(item.book.favicon))

      containerView.setOnClickListener {
        clickAction.invoke(item)
      }
    }

  }

  class LibraryDividerViewHolder(view: View) : LibraryViewHolder<DividerItem>(view) {
    override fun bind(item: DividerItem) {
      divider_text.text = item.text
    }
  }

}
