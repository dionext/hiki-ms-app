package com.dionext.hiki.services;

import com.dionext.hiki.db.entity.ImageGalleryItem;
import com.dionext.hiki.db.repositories.ImageGalleryItemRepository;
import com.dionext.site.dto.SrcPageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"java:S5663"})
public class ImageGalleryCreatorService extends HikingLandPageCreatorService {

    private static int padSize = 100;
    private static String pageName = "gallery";
    ImageGalleryItemRepository imageGalleryItemRepository;

    @Autowired
    public void setImageGalleryItemRepository(ImageGalleryItemRepository imageGalleryItemRepository) {
        this.imageGalleryItemRepository = imageGalleryItemRepository;
    }

    public String createImageGalleryPage() {
        String imageGalleryName = this.pageInfo.getRelativePath();
        int dotIndex = imageGalleryName.lastIndexOf(".htm");
        if (dotIndex > -1) {
            imageGalleryName = imageGalleryName.substring(0, dotIndex);
        }
        int pageNum = 0;
        int pIndex = imageGalleryName.lastIndexOf("_p_");
        if (pIndex > -1) {
            pageNum = Integer.parseInt(imageGalleryName.substring(pIndex + 3));
        }

        StringBuilder body = new StringBuilder();
        //https://www.baeldung.com/spring-data-jpa-pagination-sorting
        Pageable pageable = PageRequest.of(pageNum, padSize, Sort.by("imageGalleryItemId"));
        Page<com.dionext.hiki.db.entity.ImageGalleryItem> listPage = imageGalleryItemRepository.findAll(pageable);
        int allPagesCount = listPage.getTotalPages();
        listPage.getTotalElements();

        String paginationStr = createPagination(pageName,
                pageInfo.getSiteSettings().getListPageDelimiter(), pageInfo.getExtension(), pageNum, allPagesCount);
        body.append(paginationStr);
        body.append("""
                <div class="row">""");
        for (ImageGalleryItem item : listPage) {
            if (item.getFullSizePath() != null) {
                body.append("""
                        <a data-toggle="lightbox" data-gallery="gallery" class="col-md-4" """); //data-gallery="mixedgallery"  data-gallery="example-gallery" - For grouping elements
                body.append("href=\"");
                body.append(pageInfo.getOffsetStringToContextLevel());
                body.append(item.getFullSizePath());
                body.append("\"");
                if (item.getTitle() != null) {
                    body.append("""
                            data-title=\"""");
                    body.append(item.getTitle());
                    body.append("\"");
                }
                body.append("""
                        >"""); //Note: uses modal plugin limiting via data-max-width (or data-max-height) - действуют при открытиии картники по клику
            }
            body.append("""
                    <img class="img-fluid rounded" src=\"""");
            body.append(pageInfo.getOffsetStringToContextLevel());
            body.append(item.getTumbPath());
            body.append("\"");
            if (item.getTitle() != null) {
                body.append("""
                         alt="
                        """.stripTrailing());
                body.append(item.getTitle());
                body.append("\"");
            }
            body.append(">");
            if (item.getFullSizePath() != null) {
                body.append("</a>");
            }
            if (item.getCaption() != null) {
                body.append(item.getCaption());
            }
        }
        body.append("</div>");
        body.append(paginationStr);

        return createHtmlAll(new SrcPageContent(body.toString(), i18n.getString("imagegallery.image.gallery", pageInfo.getLocale())));
    }

    @Override
    public String createHeadBottom() {
        return dfs(super.createHeadBottom()) +
                """
                        <link href="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.css" rel="stylesheet"/>
                               """;
    }

    @Override
    public String createBodyScripts() {

        return dfs(super.createBodyScripts()) +
                """
                        <script src="https://cdn.jsdelivr.net/npm/bs5-lightbox@1.8.3/dist/index.bundle.min.js"></script>
                        """ +
                """
                         <script>
                            $(document).on("click", '[data-toggle="lightbox"]', function(event) {
                              event.preventDefault();
                              $(this).ekkoLightbox();
                            });
                        </script>""";
    }

}
