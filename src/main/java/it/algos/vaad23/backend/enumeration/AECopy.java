package it.algos.vaad23.backend.enumeration;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 21-gen-2021
 * Time: 14:10
 */
public enum AECopy {
    fileSoloSeNonEsiste, fileSovrascriveSempreAncheSeEsiste, fileCheckFlagSeEsiste, dirDeletingAll, dirAddingOnly, dirSoloSeNonEsiste;

    public static List<AECopy> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

}
