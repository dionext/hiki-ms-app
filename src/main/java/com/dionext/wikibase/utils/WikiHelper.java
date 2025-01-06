package com.dionext.wikibase.utils;

import com.dionext.hiki.db.entity.JGeoWikidata;

import java.util.Set;

public class WikiHelper {
    private static boolean IsPointInPolygon(PointF[] polygon, PointF point)
    {
        boolean isInside = false;
        for (int i = 0, j = polygon.length - 1; i < polygon.length; j = i++)
        {
            if (((polygon[i].Y > point.Y) != (polygon[j].Y > point.Y)) && (point.X < (polygon[j].X - polygon[i].X) * (point.Y - polygon[i].Y) / (polygon[j].Y - polygon[i].Y) + polygon[i].X))
            {
                isInside = !isInside;
            }
        }
        return isInside;
    }
    private static String FindNearestChildImageForPlace(JGeoWikidata parent, Set<String> checkDoubleSet)
    {
        /*
        String imageStr = null;
        imageStr = parent.Image;
        if (PlacesSiteCreator.IsValidImage(imageStr))
        {
            return imageStr;
        }
        //нулевая итерация ищет столицу
        if (parent.Capital != null)
        {
            JGeoWikidata capitalItem = Dm.Instance.<JGeoWikidata>Find(parent.Capital);
            if (capitalItem != null)
            {
                imageStr = capitalItem.Image;
                if (PlacesSiteCreator.IsValidImage(imageStr))
                {
                    return imageStr;
                }
            }
        }

        //IList<JGeoWikidata> items = SiteDm.GetChildsItems(parent);
        List<JGeoWikidata> items = Dm.Instance.<JGeoWikidata>ResolveOneToManyRelation(parent, "Parent"); //todo dm
        //первая итерация ищет по своему уровню
        for (var item : items)
        {
            if (checkDoubleSet.contains(item.JGeoWikidataId))
            {
                System.out.println("!!!!! Item " + item.JGeoWikidataId + " allready in child set");
                continue;
            }
            imageStr = item.Image;
            if (PlacesSiteCreator.IsValidImage(imageStr))
            {
                return imageStr;
            }
        }
        //вторая итерация рекурсивная
        for (var item : items)
        {
            if (checkDoubleSet.contains(item.JGeoWikidataId))
            {
                System.out.println("!!!!! Item " + item.JGeoWikidataId + " allready in child set");
                continue;
            }
            else
            {
                checkDoubleSet.add(item.JGeoWikidataId);
            }

            String imageStr1 = FindNearestChildImageForPlace(item, checkDoubleSet);
            if (PlacesSiteCreator.IsValidImage(imageStr1))
            {
                return imageStr1;
            }
        }

         */
        return null;
    }
    //установка канонического имени
    private static void ProcessGeoDataNaming()
    {
        /*
        List<JGeoWikidata> list = Dm.Instance.<JGeoWikidata>FindAll();
        //set name, ML
        for (var item : list)
        {
            item.Name = null;
        }

        System.out.println("Start Name processing");
        HashMap<String, JGeoWikidata> set = new HashMap<String, JGeoWikidata>();
        for (var item : list)
        {
            String name = item.GetLocalFileNameForInit();
            if (set.keySet().Contains(name))
            {
                JGeoWikidata old;
                old = set.get(name);
                System.out.println("!!!!! Non unique name found: " + name + " " + item.JGeoWikidataId + " " + item.EnwikiLink + " " + old.JGeoWikidataId + " " + old.EnwikiLink);
                item.Name = name + " (" + item.JGeoWikidataId + ")";
                old.Name = old.Name + " (" + old.JGeoWikidataId + ")";
            }
            else
            {
                set.put(name, item);
                item.Name = name;
            }
        }
        Dm.Instance.<JGeoWikidata>SetEntityModified();

        System.out.println("End Name processing");

         */
    }

    //обработка координат и установка флага альпийского региона
    private static void ProcessGeoDataAlpCoord()
    {
        /*
        java.lang.Iterable<String> lines = File.ReadLines("""
            R:\\doc\\sites\\hiking\\tmp\\alps.hlg""");
        int count = lines.size();
        PointF[] polygon = new PointF[count / 2];
        int num = 0;
        float lon = 0;
        float lat = 0;
        boolean odd = false;
        for (var v : lines)
        {
            String[] vv = v.split("[=]", -1);
            if (odd)
            {
                lat = Float.parseFloat(vv[1].replace('.', ','));
                polygon[num] = new PointF(lon, lat);
                num++;
                odd = false;
            }
            else
            {
                lon = Float.parseFloat(vv[1].replace('.', ','));
                odd = true;
            }
        }

        List<JGeoWikidata> list = Dm.Instance.<JGeoWikidata>FindAll();
        //set ML
        for (var item : list)
        {
            item.IsML = true;
        }

        //set level
        for (var item : list)
        {
            item.Level = item.GetLevel();
        }

        //count levels
        int[] levels = new int[20];
        for (var item : list)
        {
            levels[item.Level]++;
        }
        int i = 0;
        for (var l : levels)
        {

            System.out.println("Level " + i + " count " + l);
            i++;
        }

        int countIn = 0;
        int countOut = 0;
        for (var item : list)
        {
            if (item.Longitude != null && item.Latitude != null)
            {
                PointF point = new PointF(Float.parseFloat(item.Longitude.Replace('.', ',')), Float.parseFloat(item.Latitude.Replace('.', ',')));
                if (IsPointInPolygon(polygon, point))
                {
                    item.IsAlps = true;
                    countIn++;
                }
                else
                {
                    item.IsAlps = false;
                    countOut++;
                }
            }
            else
            {
                item.IsAlps = false;
                countOut++;
            }
        }
        System.out.println("countIn " + countIn);
        System.out.println("countOut " + countOut);
        countIn = 0;
        countOut = 0;
        for (var item : list)
        {
            item.IsCAlps = false;
        }
        for (var item : list)
        {
            boolean isApls = item.IsAlps;
            item.IsCAlps = isApls;
            if (isApls)
            {
                JGeoWikidata cur = item;
                while (cur.Parent != null)
                {
                    cur.Parent = Dm.Instance.<JGeoWikidata>Find(cur.Parent);
                    cur = cur.Parent;
                    if (cur.IsCAlps == false)
                    {
                        cur.IsCAlps = true;
                    }
                }
            }
        }
        for (var item : list)
        {
            if (item.IsCAlps)
            {
                countIn++;
            }
            else
            {
                countOut++;
            }
        }
        System.out.println("countIn C " + countIn);
        System.out.println("countOut C " + countOut);
        Dm.Instance.<JGeoWikidata>SetEntityModified();

         */
    }
    /*
    private static void LoadMediaForGeodataLocal(WebClient webClient, JGeoWikidata item, File outputDir, JobLog log, tangible.RefObject<Integer> reqCount, tangible.RefObject<Boolean> wasErrorAll)
    {
        //DotNetWikiBot.Site site,
        //JGeoMediaInfo mediaInfo = new JGeoMediaInfo();
        // mediaInfo.MediaList = new List<JGeoMediaItem>();
        wasErrorAll.refArgValue = false;
        if (!tangible.StringHelper.isNullOrEmpty(item.Image))
        {
            boolean wasError = false;
            //загружаем один имижд не больше 1280
            tangible.RefObject<Boolean> tempRef_wasError = new tangible.RefObject<Boolean>(wasError);
            LoadMediaFile(webClient, item.JGeoWikidataId, item.Image, outputDir, item.JCountryId, -1, 1280, log, reqCount, tempRef_wasError);
            wasError = tempRef_wasError.refArgValue;
            if (wasError)
            {
                wasErrorAll.refArgValue = true;
            }
        }
        if (!tangible.StringHelper.isNullOrEmpty(item.FlagImage))
        {
            boolean wasError = false;
            //загружаем один самый маленький имижд
            tangible.RefObject<Boolean> tempRef_wasError2 = new tangible.RefObject<Boolean>(wasError);
            LoadMediaFile(webClient, item.JGeoWikidataId, item.FlagImage, outputDir, item.JCountryId + "/flags", 0, -1, log, reqCount, tempRef_wasError2);
            wasError = tempRef_wasError2.refArgValue;
            if (wasError)
            {
                wasErrorAll.refArgValue = true;
            }
        }
        if (!tangible.StringHelper.isNullOrEmpty(item.CoatOfArmsImage))
        {
            boolean wasError = false;
            //загружаем один самый маленький имижд
            tangible.RefObject<Boolean> tempRef_wasError3 = new tangible.RefObject<Boolean>(wasError);
            LoadMediaFile(webClient, item.JGeoWikidataId, item.CoatOfArmsImage, outputDir, item.JCountryId + "/coat_of_arms", 0, -1, log, reqCount, tempRef_wasError3);
            wasError = tempRef_wasError3.refArgValue;
            if (wasError)
            {
                wasErrorAll.refArgValue = true;
            }
        }
        if (!tangible.StringHelper.isNullOrEmpty(item.PageBanner))
        {
            boolean wasError = false;
            //загружаем один самый большой имижд
            tangible.RefObject<Boolean> tempRef_wasError4 = new tangible.RefObject<Boolean>(wasError);
            LoadMediaFile(webClient, item.JGeoWikidataId, item.PageBanner, outputDir, item.JCountryId + "/page_banners", -1, Integer.MAX_VALUE, log, reqCount, tempRef_wasError4);
            wasError = tempRef_wasError4.refArgValue;
            if (wasError)
            {
                wasErrorAll.refArgValue = true;
            }
        }
        if (!tangible.StringHelper.isNullOrEmpty(item.LocatorMapImage))
        {
            boolean wasError = false;
            //загружаем один  имижд не больше 800
            tangible.RefObject<Boolean> tempRef_wasError5 = new tangible.RefObject<Boolean>(wasError);
            LoadMediaFile(webClient, item.JGeoWikidataId, item.LocatorMapImage, outputDir, item.JCountryId + "/locator_maps", -1, 800, log, reqCount, tempRef_wasError5);
            wasError = tempRef_wasError5.refArgValue;
            if (wasError)
            {
                wasErrorAll.refArgValue = true;
            }
        }
        if (!tangible.StringHelper.isNullOrEmpty(item.DetailMapImage))
        {
            boolean wasError = false;
            //загружаем один  имижд не больше 1280
            tangible.RefObject<Boolean> tempRef_wasError6 = new tangible.RefObject<Boolean>(wasError);
            LoadMediaFile(webClient, item.JGeoWikidataId, item.DetailMapImage, outputDir, item.JCountryId + "/detailed_maps", -1, 1280, log, reqCount, tempRef_wasError6);
            wasError = tempRef_wasError6.refArgValue;
            if (wasError)
            {
                wasErrorAll.refArgValue = true;
            }
        }
    }



		 @param webClient
		 @param srcId
		 @param fileName
		 @param outputDir
		 @param outputPrefix
		 @param minSize Если -1, то игнорируется. Если задан, то будет просмотрен список начиная сначала. И загружен
		 один (!) первый имидж размер которого >= minSize. Таким образом если задать 0, то будет загружен самый мальнький имидж
		 @param maxSize Если -1, то игнорируется. Если задан, то будет просмотрен список начиная с конца. И загружен
		 один (!) первый имидж размер которого <= maxSize. Таким образом если задать 0, то будет загружен самый большой имидж
		 (если имиджи по minSize и maxSize совпадут, то загрузка произойдет один раз т.к. перед загрузкой проверяется, что имидж ещё не загружен)
		 @param log
		 @param reqCount
		 @param wasError
    public static void LoadMediaFile(WebClient webClient, String srcId, String fileName, File outputDir, String outputPrefix, int minSize, int maxSize, JobLog log, tangible.RefObject<Integer> reqCount, tangible.RefObject<Boolean> wasError)
    {
        //DotNetWikiBot.Site site,
        try
        {
            wasError.refArgValue = false;
            if (tangible.StringHelper.isNullOrEmpty(fileName))
            {
                return;
            }
            String dirPath = outputDir.getPath();
            if (outputPrefix != null)
            {
                dirPath = Paths.get(dirPath).resolve(outputPrefix).toString();
            }
            //filePath = Path.Combine(filePath, fileName);
            if ((new File(dirPath)).isFile() == false)
            {
                FileUtils.CreateDirectory(dirPath);
            }
            log.Debug("Loading: " + fileName);
            int reqCount1 = 0;

				//DotNetWikiBot.Page p = new DotNetWikiBot.Page(site, "File:" + fileName);
				//p.Load(); - не нужно
				//WikiImageInfo info = p.DownloadAllResolutionImage(srcId, dirPath, minSize, maxSize, false, interReqDelay, out reqCount1, log);//работает без ошибок только с DotNetWikiBot

            tangible.OutObject<Integer> tempOut_reqCount1 = new tangible.OutObject<Integer>();
            WikiImageInfo info = DotNetWikiBot.Page.DownloadAllResolutionImageStatic(webClient, "File:" + fileName, srcId, dirPath, minSize, maxSize, false, interReqDelay, tempOut_reqCount1, log); //работает без ошибок только с DotNetWikiBot
            reqCount1 = tempOut_reqCount1.outArgValue;
            reqCount.refArgValue = reqCount.refArgValue + reqCount1;
        }
        catch (RuntimeException ex)
        {
            log.Error("Error loading file:  " + fileName, ex);
            wasError.refArgValue = true;
        }
    }
     */

}
