package org.kc7bfi.jflac.metadata;

/**
 * libFLAC - Free Lossless Audio Codec library
 * Copyright (C) 2001,2002,2003  Josh Coalson
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 */

import java.io.IOException;

import org.kc7bfi.jflac.util.InputBitStream;

public class SeekPoint {

    static final private int SEEKPOINT_SAMPLE_NUMBER_LEN = 64; // bits
    static final private int SEEKPOINT_STREAM_OFFSET_LEN = 64; // bits
    static final private int SEEKPOINT_FRAME_SAMPLES_LEN = 16; // bits

    protected long sampleNumber; // The sample number of the target frame.
    protected long streamOffset; // The offset, in bytes, of the target frame with respect to beginning of the first frame.
    protected int frameSamples; // The number of samples in the target frame.
    
    public SeekPoint(InputBitStream is) throws IOException {
        sampleNumber = is.readRawLong(SEEKPOINT_SAMPLE_NUMBER_LEN);
        streamOffset = is.readRawLong(SEEKPOINT_STREAM_OFFSET_LEN);
        frameSamples = is.readRawUInt(SEEKPOINT_FRAME_SAMPLES_LEN);
    }

    /* used as the sort predicate for qsort() */
    int compare(SeekPoint r) {
        /* we don't just 'return l->sample_number - r->sample_number' since the result (int64) might overflow an 'int' */
        if (sampleNumber == r.sampleNumber) return 0;
        if (sampleNumber < r.sampleNumber) return -1;
        return 1;
    }
}
