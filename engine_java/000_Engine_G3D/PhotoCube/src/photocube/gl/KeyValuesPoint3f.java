/**
 * Copyright (c) 2006, Sun Microsystems, Inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following 
 *     disclaimer in the documentation and/or other materials provided 
 *     with the distribution.
 *   * Neither the name of the PhotoCube project nor the names of its
 *     contributors may be used to endorse or promote products derived 
 *     from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package photocube.gl;

import java.lang.reflect.Method;
import org.jdesktop.animation.timing.interpolation.KeyValues;

/**
 * A KeyValues wrapper for our Point3f class.  This allows us to easily
 * interpolate a Point3f value using timingframework's PropertyRange and
 * ObjectModifier classes.
 *
 * @author Chris Campbell
 */
public class KeyValuesPoint3f extends KeyValues<Point3f> {

    public KeyValuesPoint3f(Point3f... values) {
        super(values);
        for (Point3f value : values) {
            this.values.add(value);
        }
    }
    
    @Override
    public Class<?> getType() {
        return Point3f.class;
    }

    @Override
    public void setValue(Object object, Method method, int i0,
                         int i1, float fraction)
    {
        // REMIND: this doesn't yet handle startValue...
        Point3f value = values.get(i0);
        if (i0 != i1) {
            Point3f v0 = value;
            Point3f v1 = values.get(i1);
            float x = v0.getX() + ((v1.getX() - v0.getX()) * fraction + .5f);
            float y = v0.getY() + ((v1.getY() - v0.getY()) * fraction + .5f);
            float z = v0.getZ() + ((v1.getZ() - v0.getZ()) * fraction + .5f);
            value = new Point3f(x, y, z);
        }
        try {
            method.invoke(object, value);
        } catch (Exception e) {
            System.err.println("Problem invoking method in KVPoint3f.setValue:" + e);
        }
    }   
    
    @Override
    public void setValue(Object object, Method method, int index) {
        try {
            method.invoke(object, values.get(index));
        } catch (Exception e) {
            System.err.println("Problem invoking method in KVPoint3f.setValue:" + e);
        }
    }    
}
